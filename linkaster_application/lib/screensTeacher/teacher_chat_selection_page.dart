import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '../models/chat.dart';
import 'teacher_private_chats_page.dart';

class TeacherChatSelectionPage extends StatefulWidget {
  final String? token;

  TeacherChatSelectionPage({Key? key, required this.token}) : super(key: key);

  @override
  _TeacherChatSelectionPageState createState() =>
      _TeacherChatSelectionPageState();
}

class _TeacherChatSelectionPageState extends State<TeacherChatSelectionPage> {
  List<Chat> privateChats = [];
  bool isLoading = false;

  @override
  void initState() {
    super.initState();
    _fetchPrivateChats();
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
        title: Text('Teacher Private Chats'),
        backgroundColor: Theme.of(context).primaryColor,
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
}
