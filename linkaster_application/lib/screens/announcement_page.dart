import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AnnouncementPage extends StatefulWidget {
  String? token;

  AnnouncementPage({required this.token});

  @override
  _AnnouncementPageState createState() => _AnnouncementPageState();
}

class _AnnouncementPageState extends State<AnnouncementPage> {
  String? token;

  // Sample messages to simulate announcements
  List<Map<String, String>> messages = [
    {
      "sender": "Professor Smith",
      "message": "Don't forget to submit your assignments by Friday!",
      "time": "10:00 AM",
    },
    {
      "sender": "Professor Johnson",
      "message": "The exam schedule has been updated. Please check the portal.",
      "time": "11:15 AM",
    },
  ];

  // Sample list of classes or clubs
  // Get /api/module/
  final List<String> classes = [
    'Software Engineering',
    'Data Science',
    'Computer Science Club',
    'Robotics Club',
    'Web Development'
  ];

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

    // Fetch announcements or perform actions based on the token
    if (token != null) {
      _fetchAnnouncements();
    }
  }

  Future<void> _fetchAnnouncements() async {
    // Simulate API call using token
    // Here, replace with your API call logic
    print("Fetching announcements with token: $token");

    // Example: Update the list of messages from API response
    setState(() {
      messages.addAll([
        {
          "sender": "Professor Lee",
          "message": "Extra credit opportunity available! Check your email.",
          "time": "1:30 PM",
        },
        {
          "sender": "Professor Kim",
          "message": "Class will be held online next week due to maintenance.",
          "time": "2:45 PM",
        },
      ]);
    });
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
                        message["sender"] ?? '',
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
        onPressed: () => _showAddAnnouncementDialog(context),
        child: Icon(Icons.add),
        backgroundColor: Theme.of(context).primaryColor,
      ),
    );
  }

  void _showAddAnnouncementDialog(BuildContext context) {
    final TextEditingController messageController = TextEditingController();
    String? selectedClass;

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
                value: selectedClass,
                onChanged: (String? newValue) {
                  setState(() {
                    selectedClass = newValue;
                  });
                },
                items: classes.map((String className) {
                  return DropdownMenuItem<String>(
                    value: className,
                    child: Text(className),
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
                if (selectedClass != null &&
                    messageController.text.isNotEmpty) {
                  // Save announcement with token validation logic here
                  print(
                      'Class: $selectedClass, Message: ${messageController.text}, Token: $token');
                  Navigator.of(context).pop();

                  // Update the UI with new announcement
                  setState(() {
                    messages.add({
                      "sender": "You",
                      "message": messageController.text,
                      "time": "Now",
                    });
                  });
                } else {
                  // Show error if validation fails
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
