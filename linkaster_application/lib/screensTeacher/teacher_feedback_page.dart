/*
 *  Title: teacher_feedback_page.dart
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */

import 'package:flutter/material.dart';

class TeacherFeedbackPage extends StatelessWidget {
  // Mock data - Replace this with your actual data fetching logic
  final List<FeedbackItem> feedbackItems = [
    FeedbackItem(
      senderName: 'Anonymous Student',
      message:
          'Great lectures and very helpful office hours. Thank you for being so approachable.',
      timestamp: DateTime.now().subtract(Duration(days: 1)),
      isAnonymous: true,
    ),
    FeedbackItem(
      senderName: 'John Doe',
      message:
          'The practical examples in class really helped me understand the concepts better.',
      timestamp: DateTime.now().subtract(Duration(days: 2)),
      isAnonymous: false,
    ),
    // Add more feedback items as needed
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Feedback Received'),
        backgroundColor: Theme.of(context).primaryColor,
      ),
      body: feedbackItems.isEmpty
          ? Center(
              child: Text(
                'No feedback received yet',
                style: TextStyle(fontSize: 16, color: Colors.grey),
              ),
            )
          : ListView.builder(
              padding: EdgeInsets.all(16.0),
              itemCount: feedbackItems.length,
              itemBuilder: (context, index) {
                final feedback = feedbackItems[index];
                return FeedbackCard(feedback: feedback);
              },
            ),
    );
  }
}

class FeedbackCard extends StatelessWidget {
  final FeedbackItem feedback;

  const FeedbackCard({Key? key, required this.feedback}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: EdgeInsets.only(bottom: 16.0),
      elevation: 2,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(12),
      ),
      child: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              feedback.isAnonymous ? 'Anonymous' : feedback.senderName,
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
                fontStyle:
                    feedback.isAnonymous ? FontStyle.italic : FontStyle.normal,
              ),
            ),
            SizedBox(height: 8),
            Text(
              feedback.message,
              style: TextStyle(fontSize: 15),
            ),
          ],
        ),
      ),
    );
  }
}

class FeedbackItem {
  final String senderName;
  final String message;
  final DateTime timestamp;
  final bool isAnonymous;

  FeedbackItem({
    required this.senderName,
    required this.message,
    required this.timestamp,
    required this.isAnonymous,
  });
}
