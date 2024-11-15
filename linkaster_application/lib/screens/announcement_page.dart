import 'package:flutter/material.dart';

class AnnouncementPage extends StatelessWidget {
  // Sample messages to simulate announcements
  final List<Map<String, String>> messages = [
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
    {
      "sender": "Professor Lee",
      "message":
          "Extra credit opportunity available! Check the details in your email.",
      "time": "1:30 PM",
    },
    {
      "sender": "Professor Kim",
      "message": "Class will be held online next week due to maintenance.",
      "time": "2:45 PM",
    },
  ];

  // Sample list of classes or clubs
  final List<String> classes = [
    'Software Engineering',
    'Data Science',
    'Computer Science Club',
    'Robotics Club',
    'Web Development'
  ];

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

  // Function to show a dialog to add a new announcement
  void _showAddAnnouncementDialog(BuildContext context) {
    final TextEditingController messageController = TextEditingController();
    String? selectedClass; // Variable to hold selected class/club

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
                  selectedClass = newValue;
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
                  // Add your logic to handle saving the announcement here
                  print(
                      'Class: $selectedClass, Message: ${messageController.text}');
                  Navigator.of(context).pop();
                } else {
                  // Optionally, show a snackbar or alert if validation fails
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(
                        content:
                            Text('Please select a class and enter a message.')),
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
