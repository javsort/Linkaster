import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

import '../widget/user_status.dart';
import '../widget/create_module_club.dart';
import '../widget/join_module.dart'; // Import the JoinModuleDialog widget

import 'announcement_page.dart';
import 'chat_selection_page.dart';
import 'profile_page.dart';
import 'settings_page.dart';
import 'feedback_page.dart';
import 'timetable_page.dart';
import 'logIn_page.dart'; // Import your LoginPage

class LinkasterHome extends StatefulWidget {
  @override
  LinkasterHomeState createState() => LinkasterHomeState();
}

class LinkasterHomeState extends State<LinkasterHome> {
  int _currentIndex = 0;
  String? token;

  @override
  void initState() {
    super.initState();
    _checkAuthToken();
  }

  Future<void> _checkAuthToken() async {
    final prefs = await SharedPreferences.getInstance();
    setState(() {
      token = prefs.getString('authToken');
      if (token == null || token!.isEmpty) {
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => LoginPage()),
        );
      } else {
        _updatePages();
      }
    });
  }

  final List<Widget> _pages = [];

  void _updatePages() {
    setState(() {
      _pages.clear();
      _pages.addAll([
        AnnouncementPage(token: token),
        ChatSelectionPage(isPrivateChat: true, token: token),
        ChatSelectionPage(isPrivateChat: false, token: token),
        TimetablePage(token: token),
        ProfilePage(token: token, status: UserStatus.available),
      ]);
    });
  }

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
          MaterialPageRoute(builder: (context) => FeedbackPage(token: token)),
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
    const url = 'https://portal.lancaster.ac.uk/portal/my-area/modules';
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
        return CreateModuleDialog(
          token: token,
        );
      },
    );
  }

  void _showJoinModuleDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return JoinModuleDialog(
          token: token,
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
            icon: Text('Create Module', style: TextStyle(color: Colors.white)),
            onPressed: _showCreateModuleDialog,
          ),
          IconButton(
            icon: Text('Join Module', style: TextStyle(color: Colors.white)),
            onPressed: _showJoinModuleDialog,
          ),
          IconButton(
            icon: Text('Moodle', style: TextStyle(color: Colors.white)),
            onPressed: _launchMoodle,
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
      body: _pages.isNotEmpty
          ? _pages[_currentIndex]
          : Center(child: CircularProgressIndicator()),
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
