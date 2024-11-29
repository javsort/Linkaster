import 'package:flutter/material.dart';
import '../models/chat.dart';

class PrivateChatPage extends StatelessWidget {
  final Chat chat;
  String? token;

  PrivateChatPage({required this.chat, required this.token});

  // Sample messages for demonstration
  final List<Map<String, dynamic>> messages = [
    {"isSent": true, "message": "Hello! How are you?", "time": "10:01 AM"},
    {
      "isSent": false,
      "message": "I'm good, thanks! How about you?",
      "time": "10:02 AM"
    },
  ];

  @override
  Widget build(BuildContext context) {
    print('PR Token: $token');
    return Scaffold(
      appBar: AppBar(
        title: Row(
          children: [
            CircleAvatar(
              backgroundImage: NetworkImage(chat.avatar),
              radius: 20,
            ),
            SizedBox(width: 10),
            Text(chat.name),
          ],
        ),
        backgroundColor: Theme.of(context).primaryColor,
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              padding: EdgeInsets.symmetric(vertical: 10, horizontal: 8),
              itemCount: messages.length,
              itemBuilder: (context, index) {
                final message = messages[index];
                return Align(
                  alignment: message['isSent']
                      ? Alignment.centerRight
                      : Alignment.centerLeft,
                  child: Container(
                    margin: EdgeInsets.symmetric(vertical: 4, horizontal: 12),
                    padding: EdgeInsets.symmetric(vertical: 10, horizontal: 14),
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
                            style: TextStyle(fontSize: 12, color: Colors.grey)),
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
          // Expanded TextField for entering a message
          Expanded(
            child: TextField(
              decoration: InputDecoration(
                hintText: 'Type a message',
                border:
                    OutlineInputBorder(borderRadius: BorderRadius.circular(30)),
              ),
            ),
          ),
          // Attachment icon button (for future functionality)
          IconButton(
            icon: Icon(Icons.attach_file, color: Colors.grey),
            onPressed: () {
              // TODO: Add functionality for attaching a file
            },
          ),
          // Send icon button
          IconButton(
            icon: Icon(Icons.send, color: Colors.blue),
            onPressed: () {
              // TODO: Implement send message functionality
            },
          ),
        ],
      ),
    );
  }
}
