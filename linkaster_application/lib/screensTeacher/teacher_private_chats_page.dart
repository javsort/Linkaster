import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../models/chat.dart';

class TeacherPrivateChatsPage extends StatefulWidget {
  final Chat chat;
  final String? token;

  TeacherPrivateChatsPage({Key? key, required this.chat, required this.token})
      : super(key: key);

  @override
  _TeacherPrivateChatsPageState createState() =>
      _TeacherPrivateChatsPageState();
}

class _TeacherPrivateChatsPageState extends State<TeacherPrivateChatsPage> {
  List<Map<String, dynamic>> messages = [];
  bool isLoading = false;

  @override
  void initState() {
    super.initState();
    _fetchMessages();
  }

  Future<void> _fetchMessages() async {
    setState(() {
      isLoading = true;
    });

    final apiUrl =
        "http://localhost:8080/api/message/private/${widget.chat.privateChatId}";
    final token = widget.token;

    try {
      final response = await http.get(
        Uri.parse(apiUrl),
        headers: {'Authorization': 'Bearer $token'},
      );

      if (response.statusCode == 200) {
        final List<dynamic> fetchedMessages = jsonDecode(response.body);
        setState(() {
          messages = fetchedMessages.map((msg) {
            return {
              'isSent': msg['senderId'] == widget.chat.senderId,
              'message': msg['encryptedMessage'],
              'time': DateTime.parse(msg['timestamp'])
                  .toLocal()
                  .toString()
                  .split(' ')[1],
            };
          }).toList();
        });
      } else {
        print("Failed to load messages: ${response.statusCode}");
      }
    } catch (e) {
      print("Error fetching messages: $e");
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
        title: Text(widget.chat.receiverName),
        backgroundColor: Theme.of(context).primaryColor,
      ),
      body: Column(
        children: [
          Expanded(
            child: isLoading
                ? Center(child: CircularProgressIndicator())
                : ListView.builder(
                    itemCount: messages.length,
                    itemBuilder: (context, index) {
                      final message = messages[index];
                      return Align(
                        alignment: message['isSent']
                            ? Alignment.centerRight
                            : Alignment.centerLeft,
                        child: Container(
                          margin:
                              EdgeInsets.symmetric(vertical: 4, horizontal: 12),
                          padding:
                              EdgeInsets.symmetric(vertical: 10, horizontal: 14),
                          decoration: BoxDecoration(
                            color: message['isSent']
                                ? Colors.red[100]
                                : Colors.grey[200],
                            borderRadius: BorderRadius.circular(16),
                          ),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(message['message'],
                                  style: TextStyle(fontSize: 16)),
                              SizedBox(height: 4),
                              Text(message['time'],
                                  style: TextStyle(
                                      fontSize: 12, color: Colors.grey)),
                            ],
                          ),
                        ),
                      );
                    },
                  ),
          ),
          _buildMessageInputField(),
        ],
      ),
    );
  }

  Widget _buildMessageInputField() {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Row(
        children: [
          Expanded(
            child: TextField(
              decoration: InputDecoration(
                hintText: 'Type a message',
                border:
                    OutlineInputBorder(borderRadius: BorderRadius.circular(30)),
              ),
            ),
          ),
          IconButton(
            icon: Icon(Icons.send, color: Colors.blue),
            onPressed: () {
              // Add send message logic here
            },
          ),
        ],
      ),
    );
  }
}
