import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:linkaster_application/config/config.dart';
import 'package:shared_preferences/shared_preferences.dart';

class FeedbackPage extends StatefulWidget {
  final String? token; // Token passed to the FeedbackPage

  FeedbackPage({required this.token});

  @override
  _FeedbackPageState createState() => _FeedbackPageState();
}

class _FeedbackPageState extends State<FeedbackPage> {
  List<String> recipients = []; // Stores "Teacher - Module" format
  String? token;
  List<Map<String, String>> classesStudents = []; // Stores module details
  String? selectedRecipient;
  bool isAnonymous = false;
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
      await _fetchClasses("2"); // Replace "2" with actual user ID
    }
  }

  Future<void> _fetchClasses(String userID) async {
    final url = Uri.parse('${AppConfig.apiBaseUrl}/api/module/students/$userID');
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
        // Populate `classesStudents` and `recipients`
        classesStudents = moduleList.map<Map<String, String>>((module) {
          return {
            'moduleId': module['moduleId'].toString(),
            'moduleName': module['moduleName'],
            'teacherId': module['teacherId'].toString(),
            'teacherName': module['teacherName'],
          };
        }).toList();

        recipients = classesStudents.map<String>((module) {
          final moduleName = module['moduleName']!;
          final teacherName = module['teacherName']!;
          return '$teacherName - $moduleName';
        }).toList();
      });
    } else {
      print('Failed to fetch classes: ${response.statusCode}');
    }
  }

  Future<void> _submitFeedback() async {
    if (selectedRecipient == null || feedbackController.text.isEmpty) {
      _showError('Please fill in all fields before submitting.');
      return;
    }

    final parts = selectedRecipient?.split(' - ');
    if (parts == null || parts.length != 2) {
      _showError('Invalid recipient format.');
      return;
    }

    final teacherName = parts[0];
    final moduleName = parts[1];

    // Find matching module
    final module = classesStudents.firstWhere(
      (mod) => mod['moduleName'] == moduleName && mod['teacherName'] == teacherName,
      orElse: () => {},
    );

    if (module.isEmpty) {
      _showError('Could not find the selected module or teacher.');
      return;
    }

    final recipientID = module['teacherId'];
    final moduleID = module['moduleId'];
    final senderID = "YourUserID"; // Replace with the current user's ID
    final contents = feedbackController.text;

    try {
      final url = Uri.parse('${AppConfig.apiBaseUrl}/api/feedback/submit');
      final response = await http.post(
        url,
        headers: {
          'Authorization': 'Bearer $token',
          'Content-Type': 'application/json',
        },
        body: jsonEncode({
          'recipientID': recipientID,
          'senderID': senderID,
          'anonymous': isAnonymous,
          'moduleID': moduleID,
          'contents': contents,
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
