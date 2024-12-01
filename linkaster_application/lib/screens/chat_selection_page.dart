import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '../models/chat.dart';
import '../models/group_chat.dart';
import 'private_chats_page.dart';

class ChatSelectionPage extends StatefulWidget {
  final bool isPrivateChat;
  final String? token;

  ChatSelectionPage({Key? key, required this.isPrivateChat, required this.token})
      : super(key: key);

  @override
  _ChatSelectionPageState createState() => _ChatSelectionPageState();
}

class _ChatSelectionPageState extends State<ChatSelectionPage> {
  List<Chat> privateChats = [];
  bool isLoading = false;

  @override
  void initState() {
    super.initState();
    if (widget.isPrivateChat) {
      _fetchPrivateChats();
    }
  }

  Future<void> _fetchPrivateChats() async {
    setState(() {
      isLoading = true;
    });

    final apiUrl = "http://localhost:8080/api/message/private/all";
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
        print("Failed to fetch private chats: ${response.statusCode}");
      }
    } catch (e) {
      print("Error fetching private chats: $e");
    } finally {
      setState(() {
        isLoading = false;
      });
    }
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
                        builder: (context) => PrivateChatPage(
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
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Search for a contact'),
          content: TextField(
            decoration: InputDecoration(hintText: 'Search by name'),
            onChanged: (query) {
              // Handle search logic here
            },
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
                Navigator.pop(context);
              },
              child: Text('Search'),
            ),
          ],
        );
      },
    );
  }
}
