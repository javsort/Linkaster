import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import '../config/config.dart';

class AnnouncementPage extends StatefulWidget {
  final String? token;

  AnnouncementPage({required this.token});

  @override
  _AnnouncementPageState createState() => _AnnouncementPageState();
}

class _AnnouncementPageState extends State<AnnouncementPage> {
  String? token;
  List<Map<String, dynamic>> messages = [];
  List<Map<String, dynamic>> classesStudents =
      []; // Stores classes with name and ID
  String? selectedClassId; // Selected module ID as String

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
      await _fetchAnnouncements();
      await _fetchClasses(); // Fetch classes after announcements
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

  Future<void> _fetchAnnouncements() async {
    final url =
        Uri.parse('${AppConfig.apiBaseUrl}/api/module/announcement/user');
    final response = await http.get(
      url,
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
    );

    if (response.statusCode == 200) {
      final List<dynamic> announcements = jsonDecode(response.body);
      setState(() {
        messages = announcements.map((announcement) {
          return {
            "id": announcement['id'] ?? '',
            "message": announcement['message'] ?? '',
            "ownerId": announcement['ownerId'] ?? '',
            "ownerName": announcement['ownerName'] ?? '',
            "time": announcement['time'] ?? '',
            "date": announcement['date'] ?? '',
            "moduleId": announcement['moduleId'] ?? '',
          };
        }).toList();
      });
    } else {
      print('Failed to fetch announcements: ${response.statusCode}');
    }
  }

  Future<void> _submitAnnouncement(String moduleId, String message) async {
    final url = Uri.parse('${AppConfig.apiBaseUrl}/api/module/announcement');
    final response = await http.post(
      url,
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
      body: jsonEncode({
        "moduleId": int.parse(moduleId), // Convert String back to int/long
        "message": message,
      }),
    );

    if (response.statusCode == 201) {
      final Map<String, dynamic> responseData = jsonDecode(response.body);
      final newMessage = {
        "id": responseData['id'],
        "message": responseData['message'],
        "ownerId": responseData['ownerId'],
        "ownerName": responseData['ownerName'],
        "time": responseData['time'],
        "date": responseData['date'],
        "moduleId": responseData['moduleId'],
      };
      setState(() {
        messages.add(newMessage);
      });
    } else {
      print('Failed to submit announcement: ${response.statusCode}');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Announcements'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            SizedBox(height: 16),
            Expanded(
              child: ListView.builder(
                itemCount: messages.length,
                itemBuilder: (context, index) {
                  final message = messages[index];
                  return Card(
                    margin: EdgeInsets.symmetric(vertical: 8),
                    child: ListTile(
                      title: Text(
                        message["ownerName"] ?? '',
                        style: TextStyle(fontWeight: FontWeight.bold),
                      ),
                      subtitle: Text(message["message"] ?? ''),
                      trailing: Text(message["time"] ?? ''),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          print('Fetching classes...');
          await _fetchClasses();
          _showAddAnnouncementDialog(context);
        },
        child: Icon(Icons.add),
        backgroundColor: Theme.of(context).primaryColor,
      ),
    );
  }

  void _showAddAnnouncementDialog(BuildContext context) {
    final TextEditingController messageController = TextEditingController();

    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: Text('Add Announcement'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              DropdownButton<String>(
                hint: Text('Select Class/Club'),
                value: selectedClassId,
                onChanged: (String? newValue) {
                  setState(() {
                    selectedClassId = newValue;
                  });
                },
                items: classesStudents.map((classItem) {
                  return DropdownMenuItem<String>(
                    value: classItem['moduleId'], // Submit this as value
                    child: Text(classItem['moduleName']), // Display this
                  );
                }).toList(),
              ),
              TextField(
                controller: messageController,
                decoration: InputDecoration(hintText: 'Message'),
                maxLines: 3,
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () {
                if (selectedClassId != null &&
                    messageController.text.isNotEmpty) {
                  _submitAnnouncement(selectedClassId!, messageController.text);
                  Navigator.of(context).pop();
                } else {
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(
                      content:
                          Text('Please select a class and enter a message.'),
                    ),
                  );
                }
              },
              child: Text('Submit'),
            ),
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: Text('Cancel'),
            ),
          ],
        );
      },
    );
  }
}
