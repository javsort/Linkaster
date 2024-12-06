/*
 *  Title: teacher_feedback_page.dart
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:linkaster_application/config/config.dart';
import 'package:shared_preferences/shared_preferences.dart';

class TeacherFeedbackPage extends StatefulWidget {

  final String? token; // Token passed to the FeedbackPage

  TeacherFeedbackPage({required this.token});

  @override
  _TeacherFeedbackPageState createState() => _TeacherFeedbackPageState();
}

class _TeacherFeedbackPageState extends State<TeacherFeedbackPage> {
  String? token;
  late Future<List<FeedbackItem>> feedbackItems;

  @override
  void initState() {
    super.initState();
    feedbackItems = fetchFeedbacks();
  }
  Future<void> _retrieveToken() async {
  final prefs = await SharedPreferences.getInstance();
  setState(() {
    token = prefs.getString('authToken');
    print('Retrieved token: $token');
  });

    if (token != null) {
      await fetchFeedbacks(); 
    }
  }
  Future<List<FeedbackItem>> fetchFeedbacks() async {
    try {
      final response = await http.post(
        Uri.parse('${AppConfig.apiBaseUrl}/api/feedback/getInstructorFeedbacks'),
        headers: {'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',},
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
              feedback.senderID == '0' ? 'Anonymous' : feedback.senderID,
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
                fontStyle:
                    feedback.senderID == '0' ? FontStyle.italic : FontStyle.normal,
              ),
            ),
            SizedBox(height: 8),
            Text(
              feedback.contents,
              style: TextStyle(fontSize: 15),
            ),
          ],
        ),
      ),
    );
  }
}

class FeedbackItem {
  final String senderID;
  final String contents;

  FeedbackItem({
    required this.senderID,
    required this.contents,
  });

  factory FeedbackItem.fromJson(Map<String, dynamic> json) {
    return FeedbackItem(
      senderID: json['sender'],
      contents: json['contents'],
    );
  }
}
