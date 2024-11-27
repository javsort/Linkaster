import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:linkaster_application/config/config.dart';
import 'package:http/http.dart' as http;


class FeedbackPage extends StatefulWidget {
  @override
  _FeedbackPageState createState() => _FeedbackPageState();
}

class _FeedbackPageState extends State<FeedbackPage> {
  String? selectedRecipient;
  bool isAnonymous = false;
  String? feedbackInput;
  final TextEditingController feedbackController = TextEditingController();

  final List<String> recipients = [
    'Dr. Smith - Software Engineering',
    'Prof. Johnson - Computer Science',
    'Ms. Brown - IT Club Leader',
    'Mr. White - Data Science Club Leader',
    'Dr. Miller - Cyber Security',
  ];

  void _submitFeedback() {
    String feedback = feedbackController.text;
    if (selectedRecipient != null && feedback.isNotEmpty) {
//added:
      //split the selected recipient into recipient name and recipient module:
      List<String> recipientParts = selectedRecipient?.split(' - ') ?? [];
      if (recipientParts.length == 2) {
      String recipientName = recipientParts[0];
      String recipientModule = recipientParts[1];

      // Handle feedback submission logic here
      // You can save to a database or display a confirmation message
      //feedback contains: feedbackID, recipientID, senderID, anonymous, moduleID, and contents
      Future<bool> registerFeedback(
        int feedbackID,
        String recipientID,
        String senderID,
        bool anonymous, 
        String moduleID,
        String contents,
        ) async {
          final response = await http.post(
            Uri.parse('${AppConfig.apiBaseUrl}/'), //insert correct path
            headers: {'Content-Type': 'application/json'},
            body: jsonEncode({
            //need to be updated:
              "feedbackID": 0,
              "recipientID": 0,
              "senderID": 0,
              "anonymous": isAnonymous,
              "moduleID": 0,
              "cotents": feedbackInput
            })
          );
          return false;
        }
//end
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Feedback submitted successfully!')),
      );
      feedbackController.clear();
      setState(() {
        selectedRecipient = null;
        isAnonymous = false;
      });
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Please fill in all fields before submitting.')),
        );
      }
    }
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
