/*
 *  Title: feedback_page.dart
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../config/config.dart';
import 'package:shared_preferences/shared_preferences.dart';

class FeedbackPage extends StatefulWidget {
  final String? token; // Token passed to the FeedbackPage

  FeedbackPage({required this.token});

  @override
  _FeedbackPageState createState() => _FeedbackPageState();
}

class _FeedbackPageState extends State<FeedbackPage> {
  List<String> recipients = [];
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
      await _fetchClasses(); 
    }
  }

  Future<void> _fetchClasses() async {
    final url = Uri.parse('${AppConfig.apiBaseUrl}/api/module/students');
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
            'moduleOwnerName': module['moduleOwnerName'],
          };
        }).toList();

        recipients = classesStudents.map<String>((module) {
          final moduleName = module['moduleName'];
          final moduleOwnerName = module['moduleOwnerName'];
          return '$moduleOwnerName - $moduleName';
        }).toList();
        
      });
    } else {
      print('Failed to fetch classes: ${response.statusCode}');
      _showError('Failed to fetch classes: (${response.statusCode})');
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

    final moduleOwnerName = parts[0];
    final moduleName = parts[1];

    // Find matching module
    final module = classesStudents.firstWhere(
      (mod) => mod['moduleName'] == moduleName && mod['moduleOwnerName'] == moduleOwnerName,
      orElse: () => {},
    );

    if (module.isEmpty) {
      _showError('Error processing request: Could not find the selected module or teacher.');
      return;
    }

    final recipientID = module['teacherId'];
    final moduleID = module['moduleId'];
    final senderID = "1";                                        // Replace with user id
    final contents = feedbackController.text;

    try {
      final url = Uri.parse('${AppConfig.apiBaseUrl}/api/feedback/submitFeedback');
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
/*
 *  Title: feedback_page.dart (UI implementation)
 *  Author: Gonzalez Fernandez, Marcos
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
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
