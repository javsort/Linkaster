import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:linkaster_application/config/config.dart';
import 'package:shared_preferences/shared_preferences.dart';

class FeedbackPage extends StatefulWidget {
  final String? token; // Token passed to the FeedbackPage

  const FeedbackPage({
    Key? key,
    required this.token,
  }) : super(key: key);

  @override
  _FeedbackPageState createState() => _FeedbackPageState();
}

class _FeedbackPageState extends State<FeedbackPage> {
  String? token;
  List<Map<String, dynamic>> classesStudents =
    []; // Stores classes with name and ID
  late TextEditingController studentIDController;
  String? selectedRecipient;
  bool isAnonymous = false;
  String? feedbackInput;
  int? studentID = 123456;
  //to do: store current user id 
  final TextEditingController feedbackController = TextEditingController();
  @override
  void initState() {
    super.initState();
    _retrieveToken();
  }

  Future<void> _retrieveToken() async {
    final prefs = await SharedPreferences.getInstance();
    setState(() {
      token = prefs.getString('authToken');
      print('Retrieved token: $token');
    });

    if (token != null) {
      await _fetchClasses(); // Fetch classes after announcements
    }
  }

  Future<void> _fetchClasses() async {  
    final url = Uri.parse('${AppConfig.apiBaseUrl}/api/module/students');  //UPDATE TO ACTUAL ID
    final response = await http.get(
      url,
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
    );

    if (response.statusCode == 200) {
      print('Classes fetched: ${response.body}');
      final List<dynamic> moduleList = jsonDecode(response.body);
      setState(() {
        classesStudents = moduleList.map<Map<String, dynamic>>((module) {
          return {
            'moduleName': module['moduleName'],
            'moduleId': module['moduleId'].toString(),
          };
        }).toList();
      });
    } else {
      print('Failed to fetch classes: ${response.statusCode}');
    }
  }



  final List<String> recipients = [
    //to do: get user id's module id's
    //translate module ids to teacher - module
    //translate teacher - module to teacher id - module id
    'Dr. Smith - Software Engineering',
    'Prof. Johnson - Computer Science',
    'Ms. Brown - IT Club Leader',
    'Mr. White - Data Science Club Leader',
    'Dr. Miller - Cyber Security',
  ];

  Future<void> _submitFeedback() async {
    String feedback = feedbackController.text;

    if (selectedRecipient != null && feedback.isNotEmpty) {
      try {
        final url = Uri.parse(
            'https://example.com/api/feedback'); // Replace with your actual API URL

        final response = await http.post(
          url,
          headers: {
            'Authorization':
                'Bearer ${widget.token}', // Add token to the request
            'Content-Type': 'application/json',
          },
          body: jsonEncode({
            'recipient': selectedRecipient,
            'feedback': feedback,
            'isAnonymous': isAnonymous,
          }),
        );

        if (response.statusCode == 200) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Feedback submitted successfully!')),
          );
          feedbackController.clear();
          setState(() {
            selectedRecipient = null;
            isAnonymous = false;
          });
        } else {
          _showError('Failed to submit feedback. (${response.statusCode})');
        }
      } catch (e) {
        _showError('An error occurred while submitting feedback.');
      }
    } else {
      _showError('Please fill in all fields before submitting.');
    }
  }

  void _showError(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(message)),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Submit Feedback'),
        backgroundColor: Theme.of(context).primaryColor,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Select Recipient',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 10),
            DropdownButtonFormField<String>(
              value: selectedRecipient,
              onChanged: (newValue) {
                setState(() {
                  selectedRecipient = newValue;
                });
              },
              decoration: InputDecoration(
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                ),
                contentPadding:
                    EdgeInsets.symmetric(horizontal: 15, vertical: 15),
                labelText: 'Recipient',
              ),
              items: recipients.map((recipient) {
                return DropdownMenuItem(
                  value: recipient,
                  child: Text(recipient),
                );
              }).toList(),
            ),
            SizedBox(height: 20),
            Row(
              children: [
                Checkbox(
                  value: isAnonymous,
                  onChanged: (value) {
                    setState(() {
                      isAnonymous = value!;
                    });
                  },
                ),
                Text('Submit anonymously'),
              ],
            ),
            SizedBox(height: 20),
            TextField(
              controller: feedbackController,
              maxLines: 5,
              decoration: InputDecoration(
                labelText: 'Write your feedback',
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                ),
                //add saving feedback text as feedbackInput
              ),
            ),
            SizedBox(height: 20),
            Center(
              child: ElevatedButton(
                onPressed: _submitFeedback,
                style: ElevatedButton.styleFrom(
                  padding: EdgeInsets.symmetric(horizontal: 40, vertical: 15),
                  backgroundColor: Theme.of(context).primaryColor,
                  foregroundColor: Colors.white,
                  textStyle: TextStyle(fontSize: 16.0),
                ),
                child: Text('Submit Feedback'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
