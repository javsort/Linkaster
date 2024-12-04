import 'package:flutter/material.dart';
import '../widget/user_status.dart';
import 'package:url_launcher/url_launcher.dart';
import 'teacher_announcement_page.dart';
import 'teacher_chat_selection_page.dart'; // Correct reference
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

  final TextEditingController joinCodeController = TextEditingController();

  final List<Widget> _pages = [
    AnnouncementPage(),
    TeacherChatSelectionPage(token: null), // Pass null token for now
    TeacherChatSelectionPage(token: null), // Reuse selection page for group chats
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
          MaterialPageRoute(builder: (context) => TeacherFeedbackPage(instructorID: 1,)),
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
