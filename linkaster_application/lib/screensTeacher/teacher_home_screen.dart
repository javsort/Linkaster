import 'package:flutter/material.dart';
import '../widget/user_status.dart';
import 'package:url_launcher/url_launcher.dart';
import 'teacher_announcement_page.dart';
import 'teacher_chat_selection_page.dart';
import 'teacher_profile_page.dart';
import 'teacher_settings_page.dart';
import 'teacher_timetable_page.dart';
import 'teacher_feedback_page.dart';
import 'teacher_logIn_page.dart'; // Import your LoginPage
import 'package:shared_preferences/shared_preferences.dart';

class LinkasterHome extends StatefulWidget {
  @override
  LinkasterHomeState createState() => LinkasterHomeState();
}

class LinkasterHomeState extends State<LinkasterHome> {
  int _currentIndex = 0;

  // Token storage
  String? token;

  @override
  void initState() {
    super.initState();
    _checkAuthToken();
  }

  Future<void> _checkAuthToken() async {
    final prefs = await SharedPreferences.getInstance();
    token = prefs.getString('authToken');
    print('Token: $token');

    if (token == null || token!.isEmpty) {
      // Redirect to the login page if token is not available
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => TeacherLoginPage()),
      );
    }
  }

  final TextEditingController moduleNameController = TextEditingController();
  final TextEditingController moduleCodeController = TextEditingController();
  final TextEditingController classTimeController = TextEditingController();
  final TextEditingController joinCodeController = TextEditingController();

  final List<Widget> _pages = [
    AnnouncementPage(),
    ChatSelectionPage(isPrivateChat: true),
    ChatSelectionPage(isPrivateChat: false),
    TimetablePage(),
    TeacherProfile(
      id: '2121210',
      name: 'Aakash',
      surname: 'Ahmad',
      email: 'a.ahmad13@lancaster.ac.uk',
      status: UserStatus.available,
    ),
  ];

  void _onTabTapped(int index) {
    setState(() {
      _currentIndex = index;
    });
  }

  void _onMenuOptionSelected(String option) {
    switch (option) {
      case 'Settings':
        Navigator.push(
            context, MaterialPageRoute(builder: (context) => SettingsPage()));
        break;
      case 'Feedback':
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => TeacherFeedbackPage()),
        );
        break;
      case 'Logout':
        _handleLogout();
        break;
    }
  }

  Future<void> _handleLogout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('authToken'); // Remove the stored token
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (context) => TeacherLoginPage()),
    );
  }

  Future<void> _launchMoodle() async {
    const url =
        'https://portal.lancaster.ac.uk/portal/my-area/modules'; // Replace with your Moodle URL
    if (await canLaunch(url)) {
      await launch(url);
    } else {
      throw 'Could not launch $url';
    }
  }

  void _showCreateModuleDialog() {
    final TextEditingController moduleNameController = TextEditingController();
    final TextEditingController moduleCodeController = TextEditingController();
    final TextEditingController numEventsController = TextEditingController();

    // Lists to store event details dynamically
    List<TextEditingController> eventNameControllers = [];
    List<TextEditingController> eventStartTimeControllers = [];
    List<TextEditingController> eventEndTimeControllers = [];
    List<TextEditingController> eventDateControllers = [];
    List<TextEditingController> eventRoomControllers = [];

    int numberOfEvents = 0;

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return StatefulBuilder(
          builder: (context, setState) {
            return AlertDialog(
              title: Text('Create a New Module'),
              content: SingleChildScrollView(
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    // Module Name
                    TextField(
                      controller: moduleNameController,
                      decoration: InputDecoration(labelText: 'Module Name'),
                    ),
                    SizedBox(height: 10.0),
                    // Module Code (6 characters)
                    TextField(
                      controller: moduleCodeController,
                      decoration: InputDecoration(
                          labelText: 'Module Code (6 characters)'),
                      keyboardType: TextInputType.text,
                      maxLength: 6,
                    ),
                    SizedBox(height: 10.0),
                    // Number of Events
                    TextField(
                      controller: numEventsController,
                      decoration:
                          InputDecoration(labelText: 'Number of Events'),
                      keyboardType: TextInputType.number,
                      onChanged: (value) {
                        setState(() {
                          numberOfEvents = int.tryParse(value) ?? 0;

                          // Initialize controllers for each event
                          eventNameControllers = List.generate(
                              numberOfEvents, (_) => TextEditingController());
                          eventStartTimeControllers = List.generate(
                              numberOfEvents, (_) => TextEditingController());
                          eventEndTimeControllers = List.generate(
                              numberOfEvents, (_) => TextEditingController());
                          eventDateControllers = List.generate(
                              numberOfEvents, (_) => TextEditingController());
                          eventRoomControllers = List.generate(
                              numberOfEvents, (_) => TextEditingController());
                        });
                      },
                    ),
                    SizedBox(height: 20.0),

                    // Dynamically generate fields for each event
                    for (int i = 0; i < numberOfEvents; i++) ...[
                      Text(
                        'Event ${i + 1}',
                        style: TextStyle(fontWeight: FontWeight.bold),
                      ),
                      SizedBox(height: 10.0),
                      TextField(
                        controller: eventNameControllers[i],
                        decoration: InputDecoration(labelText: 'Event Name'),
                      ),
                      TextField(
                        controller: eventStartTimeControllers[i],
                        decoration: InputDecoration(
                            labelText: 'Event Start Time (e.g., 09:00)'),
                        keyboardType: TextInputType.datetime,
                      ),
                      TextField(
                        controller: eventEndTimeControllers[i],
                        decoration: InputDecoration(
                            labelText: 'Event End Time (e.g., 10:30)'),
                        keyboardType: TextInputType.datetime,
                      ),
                      TextField(
                        controller: eventDateControllers[i],
                        decoration: InputDecoration(
                            labelText: 'Event Date (e.g., 2024-11-20)'),
                        keyboardType: TextInputType.datetime,
                      ),
                      TextField(
                        controller: eventRoomControllers[i],
                        decoration: InputDecoration(labelText: 'Room'),
                      ),
                      SizedBox(height: 20.0),
                    ],
                  ],
                ),
              ),
              actions: [
                TextButton(
                  onPressed: () {
                    Navigator.pop(context);
                  },
                  child: Text('Cancel'),
                ),
                ElevatedButton(
                  onPressed: () {
                    // Handle Create Module logic here
                    print(
                        'Module Created: ${moduleNameController.text}, ${moduleCodeController.text}');

                    for (int i = 0; i < numberOfEvents; i++) {
                      print('Event ${i + 1}:');
                      print('Name: ${eventNameControllers[i].text}');
                      print('Start Time: ${eventStartTimeControllers[i].text}');
                      print('End Time: ${eventEndTimeControllers[i].text}');
                      print('Date: ${eventDateControllers[i].text}');
                      print('Room: ${eventRoomControllers[i].text}');
                    }

                    Navigator.pop(context);
                  },
                  child: Text('Create Module'),
                ),
              ],
            );
          },
        );
      },
    );
  }

  void _showJoinModuleDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Join a Module'),
          content: TextField(
            controller: joinCodeController,
            decoration:
                InputDecoration(labelText: 'Module Code (6 characters)'),
            keyboardType: TextInputType.number,
            maxLength: 6,
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: Text('Cancel'),
            ),
            ElevatedButton(
              onPressed: () {
                // Handle Join Module logic here
                print('Joined Module: ${joinCodeController.text}');
                Navigator.pop(context);
              },
              child: Text('Join Module'),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('L!nkaster'),
        backgroundColor: Theme.of(context).primaryColor,
        actions: [
          IconButton(
            icon: Text('Moodle', style: TextStyle(color: Colors.white)),
            onPressed: _launchMoodle,
          ),
          SizedBox(width: 10), // Add space between Moodle and next buttons
          IconButton(
            icon: Icon(Icons.add),
            onPressed: _showCreateModuleDialog,
            tooltip: 'Create Module',
          ),
          SizedBox(width: 10), // Space between Create and Join Module buttons
          IconButton(
            icon: Icon(Icons.group_add),
            onPressed: _showJoinModuleDialog,
            tooltip: 'Join Module',
          ),
          PopupMenuButton<String>(
            icon: Icon(Icons.menu),
            onSelected: _onMenuOptionSelected,
            itemBuilder: (BuildContext context) => [
              PopupMenuItem(value: 'Settings', child: Text('Settings')),
              PopupMenuItem(value: 'Feedback', child: Text('Feedback')),
              PopupMenuItem(value: 'Logout', child: Text('Logout')),
            ],
          ),
        ],
      ),
      body: _pages[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        onTap: _onTabTapped,
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.assignment),
            label: 'Announcements',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.chat),
            label: 'Private Chats',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.group),
            label: 'Group Chats',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.schedule),
            label: 'Timetable',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.account_circle),
            label: 'Profile',
          ),
        ],
      ),
    );
  }
}
