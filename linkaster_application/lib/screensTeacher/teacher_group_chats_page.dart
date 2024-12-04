import 'package:flutter/material.dart';
import 'package:web_socket_channel/web_socket_channel.dart';
import 'package:web_socket_channel/status.dart' as status;
import '../models/group_chat.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;

class GroupChatsPage extends StatefulWidget {
  final GroupChat groupChat;
  final String? token;

  GroupChatsPage({required this.groupChat, required this.token});

  @override
  _GroupChatPageState createState() => _GroupChatPageState();
}

class _GroupChatPageState extends State<GroupChatsPage> {
  late WebSocketChannel _channel;
  late List<Map<String, dynamic>> messages;
  final TextEditingController _messageController = TextEditingController();

  @override
  void initState() {
    super.initState();
    messages = [];
    _fetchExistingMessages().then((_) => _connectToWebSocket());
  }

  Future<void> _fetchExistingMessages() async {
    final apiUrl =
        "http://localhost:8080/api/message/group/${widget.groupChat.groupChatId}";
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
            return {'message': msg['message'], 'time': (msg['timestamp'])};
          }).toList();
        });
      } else {
        print("Failed to load messages. Status: ${response.statusCode}");
      }
    } catch (e) {
      print("Error fetching messages: $e");
    }
  }

  @override
  void dispose() {
    _channel.sink.close(status.goingAway);
    super.dispose();
  }

  void _connectToWebSocket() {
    final uri = Uri.parse("ws://localhost:8086/ws");
    _channel = WebSocketChannel.connect(uri);

    // Send AUTH message for group chat
    final authMessage = {
      "type": "GROUP_AUTH",
      "token": widget.token,
      "chatId": widget.groupChat.groupChatId.toString(),
    };
    _channel.sink.add(jsonEncode(authMessage));

    // Listen for messages
    _channel.stream.listen(
      (message) {
        print("Received: $message");
        try {
          final decodedMessage = jsonDecode(message);

          if (decodedMessage.containsKey("message") &&
              decodedMessage.containsKey("groupChatId")) {
            setState(() {
              messages.add({
                'isSent': false,
                'message': decodedMessage['message'],
                'time': DateTime.now().toLocal().toString().split(' ')[1],
              });
            });
          } else {
            print("Invalid message structure received: $decodedMessage");
          }
        } catch (e) {
          print("Error decoding message: $e");
        }
      },
      onError: (error) => print("WebSocket Error: $error"),
      onDone: () => print("WebSocket connection closed."),
    );
  }

  void _sendMessage() {
    final message = _messageController.text.trim();
    if (message.isNotEmpty) {
      final payload = {
        "groupChatId": widget.groupChat.groupChatId.toString(),
        "message": message,
        "type": "GROUP"
      };

      _channel.sink.add(jsonEncode(payload));

      setState(() {
        messages.add({
          'isSent': true,
          'message': message,
          'time': DateTime.now().toLocal().toString().split(' ')[1],
        });
      });

      _messageController.clear();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Row(
          children: [
            CircleAvatar(
              backgroundColor: Colors.grey[300],
              child: Text(
                widget.groupChat.moduleName.isNotEmpty
                    ? widget.groupChat.moduleName[0].toUpperCase()
                    : "?",
                style: TextStyle(color: Colors.black),
              ),
              radius: 20,
            ),
            SizedBox(width: 10),
            Text(widget.groupChat.moduleName),
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
          Expanded(
            child: TextField(
              controller: _messageController,
              decoration: InputDecoration(
                hintText: 'Type a message',
                border:
                    OutlineInputBorder(borderRadius: BorderRadius.circular(30)),
              ),
            ),
          ),
          IconButton(
            icon: Icon(Icons.attach_file, color: Colors.grey),
            onPressed: () {
              // TODO: Add functionality for attaching a file
            },
          ),
          IconButton(
            icon: Icon(Icons.send, color: Colors.blue),
            onPressed: _sendMessage,
          ),
        ],
      ),
    );
  }
}
