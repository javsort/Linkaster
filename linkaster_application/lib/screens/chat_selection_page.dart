import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '../models/chat.dart';
import '../models/group_chat.dart';
import '../config/config.dart';
import 'private_chats_page.dart';
import 'group_chats_page.dart';

class ChatSelectionPage extends StatefulWidget {
  final bool isPrivateChat;
  final String? token;

  ChatSelectionPage({
    Key? key,
    required this.isPrivateChat,
    required this.token,
  }) : super(key: key);

  @override
  _ChatSelectionPageState createState() => _ChatSelectionPageState();
}

class _ChatSelectionPageState extends State<ChatSelectionPage> {
  List<Chat> privateChats = [];
  List<GroupChat> groupChats = [];
  bool isLoading = false;

  @override
  void initState() {
    super.initState();
    if (widget.isPrivateChat) {
      _fetchPrivateChats();
    } else {
      _fetchGroupChats();
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
          groupChats = data.map((item) => GroupChat.fromJson(item)).toList();
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

  void _showError(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(message, style: TextStyle(color: Colors.red))),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.isPrivateChat ? 'Private Chats' : 'Group Chats'),
        backgroundColor: Theme.of(context).primaryColor,
      ),
      body: isLoading
          ? Center(child: CircularProgressIndicator())
          : ListView.builder(
              itemCount: widget.isPrivateChat
                  ? privateChats.length
                  : groupChats.length,
              itemBuilder: (context, index) {
                if (widget.isPrivateChat) {
                  final chat = privateChats[index];
                  return _buildPrivateChatTile(chat);
                } else {
                  final groupChat = groupChats[index];
                  return _buildGroupChatTile(groupChat);
                }
              },
            ),
    );
  }

  Widget _buildPrivateChatTile(Chat chat) {
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
  }

  Widget _buildGroupChatTile(GroupChat groupChat) {
    return ListTile(
      leading: CircleAvatar(
        backgroundColor: Colors.blueAccent,
        child: Text(
          groupChat.moduleName.isNotEmpty
              ? groupChat.moduleName[0].toUpperCase()
              : "?",
          style: TextStyle(color: Colors.white),
        ),
      ),
      title: Text(groupChat.moduleName),
      subtitle: Text('Last message: ${groupChat.lastMessageDate}'),
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => GroupChatsPage(
              groupChat: groupChat,
              token: widget.token,
            ),
          ),
        );
      },
    );
  }
}
