/*
 *  Title: teacher_chat_selection_page.dart
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */

import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '../models/chat.dart';
import '../config/config.dart';
import 'teacher_private_chats_page.dart';
import 'teacher_group_chats_page.dart';

class TeacherChatSelectionPage extends StatefulWidget {
  final bool isPrivateChat;
  final String? token;

  TeacherChatSelectionPage(
      {Key? key, required this.isPrivateChat, required this.token})
      : super(key: key);

  @override
  _ChatSelectionPageState createState() => _ChatSelectionPageState();
}

class _ChatSelectionPageState extends State<TeacherChatSelectionPage> {
  List<Chat> privateChats = [];
  List<Chat> groupChat = [];
  bool isLoading = false;

  @override
  void initState() {
    super.initState();
    if (widget.isPrivateChat) {
      _fetchPrivateChats();
    } else if (widget.isPrivateChat == false) {
      _fetchGroupChats();
    }
  }

  Future<void> _fetchGroupChats() async {
    setState(() {
      isLoading = true;
    });

    final apiUrl = "${AppConfig.apiBaseUrl}/api/message/group/all";
    final token = widget.token;

    try {
      final response = await http.get(
        Uri.parse(apiUrl),
        headers: {'Authorization': 'Bearer $token'},
      );

      if (response.statusCode == 200) {
        final List<dynamic> data = jsonDecode(response.body);
        setState(() {
          groupChat = data.map((item) => Chat.fromJson(item)).toList();
        });
      } else {
        _showError(
            "Failed to fetch group chats. Status code: ${response.statusCode}");
      }
    } catch (e) {
      _showError("Error fetching group chats: $e");
    } finally {
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<void> _fetchPrivateChats() async {
    setState(() {
      isLoading = true;
    });

    final apiUrl = "${AppConfig.apiBaseUrl}/api/message/private/all";
    final token = widget.token;

    try {
      final response = await http.get(
        Uri.parse(apiUrl),
        headers: {'Authorization': 'Bearer $token'},
      );

      if (response.statusCode == 200) {
        final List<dynamic> data = jsonDecode(response.body);
        setState(() {
          privateChats = data.map((item) => Chat.fromJson(item)).toList();
        });
      } else {
        _showError(
            "Failed to fetch private chats. Status code: ${response.statusCode}");
      }
    } catch (e) {
      _showError("Error fetching private chats: $e");
    } finally {
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<void> _createPrivateChat(String email) async {
    final apiUrl = "${AppConfig.apiBaseUrl}/api/message/private/create";
    final token = widget.token;

    try {
      final response = await http.post(
        Uri.parse(apiUrl),
        headers: {
          'Authorization': 'Bearer $token',
          'Content-Type': 'application/json',
        },
        body: jsonEncode({'userEmail': email}),
      );

      if (response.statusCode == 201) {
        _showMessage("Chat created successfully");
        _fetchPrivateChats(); // Refresh chats
      } else {
        _showError(
            "Failed to create chat. Status code: ${response.statusCode}");
      }
    } catch (e) {
      _showError("Error creating chat: $e");
    }
  }

  void _showError(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(message, style: TextStyle(color: Colors.red))),
    );
  }

  void _showMessage(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(message)),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.isPrivateChat ? 'Private Chats' : 'Group Chats'),
        backgroundColor: Theme.of(context).primaryColor,
        actions: widget.isPrivateChat
            ? [
                IconButton(
                  icon: Icon(Icons.add),
                  onPressed: () => _showSearchDialog(context),
                  tooltip: 'New Chat',
                ),
              ]
            : [],
      ),
      body: isLoading
          ? Center(child: CircularProgressIndicator())
          : ListView.builder(
              itemCount: privateChats.length,
              itemBuilder: (context, index) {
                final chat = privateChats[index];
                return ListTile(
                  leading: CircleAvatar(
                    backgroundColor: Colors.grey[300],
                    child: Text(
                      chat.receiverName.isNotEmpty
                          ? chat.receiverName[0].toUpperCase()
                          : "?",
                      style: TextStyle(color: Colors.black),
                    ),
                  ),
                  title: Text(chat.receiverName),
                  subtitle: Text('Last active: ${chat.lastMessageDate}'),
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => TeacherPrivateChatsPage(
                          chat: chat,
                          token: widget.token,
                        ),
                      ),
                    );
                  },
                );
              },
            ),
    );
  }

  void _showSearchDialog(BuildContext context) {
    final emailController = TextEditingController();
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Search for a contact'),
          content: TextField(
            controller: emailController,
            decoration: InputDecoration(hintText: 'Enter email'),
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: Text('Cancel'),
            ),
            ElevatedButton(
              onPressed: () {
                _createPrivateChat(emailController.text);
                Navigator.pop(context);
              },
              child: Text('Create'),
            ),
          ],
        );
      },
    );
  }
}
