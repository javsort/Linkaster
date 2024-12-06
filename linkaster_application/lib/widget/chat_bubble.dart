/*
 *  Title: chat_bubble.dart
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */

import 'package:flutter/material.dart';

class ChatBubble extends StatelessWidget {
  final String senderName;
  final String message;
  final String avatarUrl;

  ChatBubble(
      {required this.senderName,
      required this.message,
      required this.avatarUrl});

  @override
  Widget build(BuildContext context) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        CircleAvatar(backgroundImage: NetworkImage(avatarUrl)),
        SizedBox(width: 8),
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(senderName, style: TextStyle(fontWeight: FontWeight.bold)),
              SizedBox(height: 4),
              Text(message),
            ],
          ),
        ),
      ],
    );
  }
}
