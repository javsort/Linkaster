import 'package:flutter/material.dart';
import '../widget/user_status.dart';
import 'package:url_launcher/url_launcher.dart';
import 'teacher_announcement_page.dart';
import 'teacher_chat_selection_page.dart';
import 'teacher_profile_page.dart';
import 'teacher_settings_page.dart';
import 'teacher_timetable_page.dart';
import 'teacher_files_page.dart';
import 'teacher_login_page.dart'; // Import your LoginPage

class LinkasterHome extends StatefulWidget {
  @override
  LinkasterHomeState createState() => LinkasterHomeState();
}

class LinkasterHomeState extends State<LinkasterHome> {
  int _currentIndex = 0;
  final TextEditingController moduleNameController = TextEditingController();
  final TextEditingController moduleCodeController = TextEditingController();
  final TextEditingController classTimeController = TextEditingController();
  final TextEditingController joinCodeController = TextEditingController();

  final List<Widget> _pages = [
    AnnouncementPage(),
    ChatSelectionPage(isPrivateChat: true),
    ChatSelectionPage(isPrivateChat: false),
    TimetablePage(),
    ProfilePage(
      name: 'Marcos',
      surname: 'Gonzalez',
      studentID: '38883104',
      studyYear: '3rd Year',
      program: 'Software Engineering',
      status: UserStatus.available,
      email: 'm.gonzalezfernandez@lancaster.ac.uk',
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
      case 'Files':
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => FilesPage()),
        );
        break;
      case 'Logout':
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => LoginPage()),
        );
        break;
    }
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
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Create a New Module'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                controller: moduleNameController,
                decoration: InputDecoration(labelText: 'Module Name'),
              ),
              TextField(
                controller: moduleCodeController,
                decoration:
                    InputDecoration(labelText: 'Module Code (6 characters)'),
                keyboardType: TextInputType.number,
                maxLength: 6,
              ),
              TextField(
                controller: classTimeController,
                decoration: InputDecoration(labelText: 'Class Time'),
              ),
            ],
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
                    'Module Created: ${moduleNameController.text}, ${moduleCodeController.text}, ${classTimeController.text}');
                Navigator.pop(context);
              },
              child: Text('Create Module'),
            ),
          ],
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
              PopupMenuItem(value: 'Files', child: Text('Files')),
              PopupMenuItem(value: 'Logout', child: Text('Logout')),
            ],
          ),
        ],
      ),
      body: _pages[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        onTap: _onTabTapped,
        type: BottomNavigationBarType.fixed,
        items: [
          BottomNavigationBarItem(
              icon: Icon(Icons.announcement), label: 'Announcements'),
          BottomNavigationBarItem(icon: Icon(Icons.chat), label: 'Chats'),
          BottomNavigationBarItem(
              icon: Icon(Icons.chat_bubble_sharp), label: 'Group Chats'),
          BottomNavigationBarItem(
              icon: Icon(Icons.calendar_month), label: 'Timetable'),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: 'Profile'),
        ],
        selectedItemColor: Theme.of(context).colorScheme.secondary,
        unselectedItemColor: Colors.grey,
      ),
    );
  }
}
