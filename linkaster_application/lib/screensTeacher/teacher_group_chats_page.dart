import 'dart:collection';

import 'package:flutter/material.dart';

//Just show the "Group Chats" page for now
class GroupChatsPage extends StatefulWidget {
  final String? token;

  GroupChatsPage({required this.token});

  @override
  _GroupChatsPageState createState() => _GroupChatsPageState();
}

class _GroupChatsPageState extends State<GroupChatsPage> {
  //Print the "Group Chats" page
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Group Chats"),
      ),
      body: Center(
        child: Text("Group Chats Page"),
      ),
    );
  }
}
