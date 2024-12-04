import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:linkaster_application/config/config.dart';

class TeacherFeedbackPage extends StatefulWidget {
  final int instructorID;

  const TeacherFeedbackPage({Key? key, required this.instructorID})
      : super(key: key);

  @override
  _TeacherFeedbackPageState createState() => _TeacherFeedbackPageState();
}

class _TeacherFeedbackPageState extends State<TeacherFeedbackPage> {
  late Future<List<FeedbackItem>> feedbackItems;

  @override
  void initState() {
    super.initState();
    feedbackItems = fetchFeedbacks();
  }

  Future<List<FeedbackItem>> fetchFeedbacks() async {
    try {
      final response = await http.post(
        Uri.parse('${AppConfig.apiBaseUrl}/api/feedback/getInstructorFeedbacks'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'instructorID': widget.instructorID}),
      );

      if (response.statusCode == 200) {
        List jsonResponse = jsonDecode(response.body);
        return jsonResponse
            .map((data) => FeedbackItem.fromJson(data))
            .toList();
      } else {
        throw Exception('Failed to load feedbacks');
      }
    } catch (error) {
      throw Exception('Error fetching feedbacks: $error');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Feedback Received'),
        backgroundColor: Theme.of(context).primaryColor,
      ),
      body: FutureBuilder<List<FeedbackItem>>(
        future: feedbackItems,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(
              child: Text(
                'Error: ${snapshot.error}',
                style: TextStyle(fontSize: 16, color: Colors.red),
              ),
            );
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return Center(
              child: Text(
                'No feedback received yet',
                style: TextStyle(fontSize: 16, color: Colors.grey),
              ),
            );
          } else {
            final feedbackList = snapshot.data!;
            return ListView.builder(
              padding: EdgeInsets.all(16.0),
              itemCount: feedbackList.length,
              itemBuilder: (context, index) {
                final feedback = feedbackList[index];
                return FeedbackCard(feedback: feedback);
              },
            );
          }
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

  factory FeedbackItem.fromJson(Map<String, dynamic> json) {
    return FeedbackItem(
      senderName: json['senderName'] ?? 'Anonymous',
      message: json['contents'],
      timestamp: DateTime.parse(json['timestamp']), // Ensure backend provides this field
      isAnonymous: json['anonymous'],
    );
  }
}
