import 'package:flutter/material.dart';
import '../models/chat.dart';
import '../models/group_chat.dart';
import '../models/group_chat_info.dart';
import 'group_chats_page.dart';
import 'private_chats_page.dart';

class ChatSelectionPage extends StatelessWidget {
  final bool isPrivateChat;
  final String? token; // Token passed to ChatSelectionPage

  // Replace these with your actual data
  final List<Chat> privateChats = [
    Chat(
        name: 'John Doe',
        lastMessage: 'Hey, how are you?',
        lastMessageTime: '10:00 AM',
        avatar: 'url_to_avatar1'),
    Chat(
        name: 'Jane Smith',
        lastMessage: 'Let’s meet tomorrow',
        lastMessageTime: '9:30 AM',
        avatar: 'url_to_avatar2'),
  ];

  final List<GroupChat> groupChats = [
    GroupChat(
        chatname: 'Internet Application Engineering',
        name: 'Jorge',
        message: 'Don’t forget the deadline!',
        time: 'Yesterday',
        avatar: 'url_to_group_avatar1'),
    GroupChat(
        chatname: 'Group Project',
        name: 'Group Project',
        message: 'Don’t forget the deadline!',
        time: 'Yesterday',
        avatar: 'url_to_group_avatar1'),
  ];

  ChatSelectionPage(
      {Key? key, required this.isPrivateChat, required this.token})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(isPrivateChat ? 'Private Chats' : 'Group Chats'),
        backgroundColor: Theme.of(context).primaryColor,
        actions: isPrivateChat
            ? [
                IconButton(
                  icon: Icon(Icons.add),
                  onPressed: () => _showSearchDialog(context),
                  tooltip: 'New Chat',
                ),
              ]
            : [],
      ),
      body: ListView.builder(
        itemCount: isPrivateChat ? privateChats.length : groupChats.length,
        itemBuilder: (context, index) {
          if (isPrivateChat) {
            final chat = privateChats[index];
            return ListTile(
              leading: CircleAvatar(
                backgroundImage: NetworkImage(chat.avatar),
              ),
              title: Text(chat.name),
              subtitle: Text('${chat.lastMessage} - ${chat.lastMessageTime}'),
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) =>
                        PrivateChatPage(chat: chat, token: token), // Pass token
                  ),
                );
              },
            );
          } else {
            final groupChat = groupChats[index];
            return ListTile(
              leading: CircleAvatar(
                backgroundImage: NetworkImage(groupChat.avatar),
              ),
              title: Text(groupChat.chatname),
              subtitle: Text('${groupChat.message} - ${groupChat.time}'),
              onTap: () {
                final groupChatInfo = GroupChatInfo(
                  chatname: groupChat.chatname,
                  name: groupChat.name,
                  time: groupChat.time,
                  moduleName: "Module Name", // Replace with actual data
                  moduleTime: "Module Time",
                  moduleCode: "Module Code",
                  studentsList: ["Jorge", "Javier", "Marcos", "Marlene"],
                  teachersList: ["Aakash", "Fabio"],
                );

                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => GroupChatPage(
                      chat: groupChat,
                      chatInfo: groupChatInfo,
                      token: token, // Pass token
                    ),
                  ),
                );
              },
            );
          }
        },
      ),
    );
  }

  // Show search dialog for creating a new chat
  void _showSearchDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Search for a contact'),
          content: TextField(
            decoration: InputDecoration(hintText: 'Search by name'),
            onChanged: (query) {
              // Handle search logic here (filter your contacts)
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
                final selectedChat = privateChats[0];
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) =>
                        PrivateChatPage(chat: selectedChat, token: token),
                  ),
                );
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
