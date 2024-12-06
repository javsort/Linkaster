/*
 *  Title: settings_page.dart
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */

import 'package:flutter/material.dart';

class SettingsPage extends StatefulWidget {
  @override
  _SettingsPageState createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  bool isDarkMode = false;

  // Base theme data (normal theme)
  final ThemeData _baseTheme = ThemeData(
    primaryColor: Color.fromARGB(255, 253, 9, 9),
    colorScheme: ColorScheme.fromSeed(
      seedColor: Color.fromARGB(255, 106, 134, 116),
      primary: Color.fromARGB(255, 253, 9, 9),
    ),
    appBarTheme: AppBarTheme(
      backgroundColor: Color.fromARGB(255, 253, 9, 9),
    ),
  );

  // Dark theme data (based on the base theme)
  final ThemeData _darkTheme = ThemeData(
    brightness: Brightness.dark,
    scaffoldBackgroundColor: Colors.black87,
    appBarTheme: AppBarTheme(
      backgroundColor: Colors.black87,
    ),
    switchTheme: SwitchThemeData(
      thumbColor: MaterialStateProperty.all(Colors.white),
    ),
  );

  void _toggleTheme(bool value) {
    setState(() {
      isDarkMode = value;
    });
  }

  @override
  Widget build(BuildContext context) {
    final ThemeData themeData = isDarkMode ? _darkTheme : _baseTheme;

    return Theme(
      data: themeData,
      child: Scaffold(
        appBar: AppBar(
          title: Text('Settings'),
          backgroundColor: Theme.of(context).primaryColor,
        ),
        body: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'App Settings',
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
              ),
              SizedBox(height: 10),
              SwitchListTile(
                title: Text('Dark Mode'),
                value: isDarkMode,
                onChanged: _toggleTheme,
              ),
              SizedBox(height: 20),
              Text(
                'Frequently Asked Questions',
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
              ),
              SizedBox(height: 10),
              Expanded(
                child: ListView(
                  children: [
                    _buildFaqItem("What is L!nkaster?",
                        "L!nkaster is a social media platform that integrates university resources, announcements, and more."),
                    _buildFaqItem("How can I change my profile information?",
                        "You can edit your profile from the Profile page."),
                    _buildFaqItem("Can I see office hours for professors?",
                        "Yes, you can see office hours for professors in the timetable."),
                    _buildFaqItem("Can I start a chat with a professor?",
                        "Only another professor can start a chat with a professor."),
                    _buildFaqItem("Can I create announcements?",
                        "If you are a member of the social committee or a club leader, you can create announcements."),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildFaqItem(String question, String answer) {
    return ExpansionTile(
      title: Text(question, style: TextStyle(fontWeight: FontWeight.w600)),
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
          child: Text(answer),
        ),
      ],
    );
  }
}
