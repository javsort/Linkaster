/*
 *  Title: profile_page.dart
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../widget/user_status.dart'; // Import your custom widget for the status indicator
import '../config/config.dart'; // Import your config file

class ProfilePage extends StatefulWidget {
  final String? token;
  final UserStatus status;

  const ProfilePage({
    Key? key,
    required this.token,
    required this.status,
  }) : super(key: key);

  @override
  _ProfilePageState createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {
  late TextEditingController nameController;
  late TextEditingController surnameController;
  late TextEditingController studentIDController;
  late TextEditingController studyYearController;
  late TextEditingController programController;
  late TextEditingController emailController;
  late TextEditingController instagramController;
  late TextEditingController linkedinController;
  late TextEditingController phoneController;

  bool isEditing = false;

  @override
  void initState() {
    super.initState();
    initializeControllers();
    fetchStudentData();
  }

  void initializeControllers({
    String name = '',
    String surname = '',
    String studentID = '',
    String studyYear = '',
    String program = '',
    String email = '',
    String instagram = '',
    String linkedin = '',
    String phone = '',
  }) {
    nameController = TextEditingController(text: name);
    surnameController = TextEditingController(text: surname);
    studentIDController = TextEditingController(text: studentID);
    studyYearController = TextEditingController(text: studyYear);
    programController = TextEditingController(text: program);
    emailController = TextEditingController(text: email);
    instagramController = TextEditingController(text: instagram);
    linkedinController = TextEditingController(text: linkedin);
    phoneController = TextEditingController(text: phone);
  }

  Future<void> fetchStudentData() async {
    final url = Uri.parse('${AppConfig.apiBaseUrl}/api/user/getStudentProfile');

    try {
      final response = await http.get(
        url,
        headers: {
          'Authorization': 'Bearer ${widget.token}',
          'Content-Type': 'application/json',
        },
      );

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);

        setState(() {
          initializeControllers(
            name: data['firstName'] ?? '',
            surname: data['lastName'] ?? '',
            studentID: data['studentId'] ?? '',
            studyYear: data['year'] ?? '',
            program: data['program'] ?? '',
            email: data['email'] ?? '',
            instagram: data['instagram'] ?? '',
            linkedin: data['linkedin'] ?? '',
            phone: data['phone'] ?? '',
          );
        });
      } else {
        _showError('Failed to load student data. (${response.statusCode})');
      }
    } catch (e) {
      _showError('An error occurred while fetching data.');
    }
  }

  void _showError(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(message),
        behavior: SnackBarBehavior.floating,
      ),
    );
  }

  void _toggleEdit() {
    if (isEditing) {
      _saveProfileChanges();
    }
    setState(() {
      isEditing = !isEditing;
    });
  }

  Future<void> _saveProfileChanges() async {
    final url = Uri.parse('${AppConfig.apiBaseUrl}/api/user/profile/update');

    final body = {
      'name': nameController.text,
      'surname': surnameController.text,
      'studentID': studentIDController.text,
      'studyYear': studyYearController.text,
      'program': programController.text,
      'email': emailController.text,
      'instagram': instagramController.text,
      'linkedin': linkedinController.text,
      'phone': phoneController.text,
    };

    try {
      final response = await http.post(
        url,
        headers: {
          'Authorization': 'Bearer ${widget.token}',
          'Content-Type': 'application/json',
        },
        body: jsonEncode(body),
      );

      if (response.statusCode == 200) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Profile updated successfully!'),
            behavior: SnackBarBehavior.floating,
          ),
        );
      } else {
        _showError('Failed to update profile. (${response.statusCode})');
      }
    } catch (e) {
      _showError('An error occurred while saving data.');
    }
  }

  Widget _buildSectionTitle(String title) {
    return Padding(
      padding: const EdgeInsets.only(top: 16.0, bottom: 8.0),
      child: Text(
        title,
        style: TextStyle(
          fontSize: 20,
          fontWeight: FontWeight.bold,
          color: Theme.of(context).primaryColor,
        ),
      ),
    );
  }

  Widget _buildInfoTile({
    required String label,
    required TextEditingController controller,
    required IconData icon,
    bool isLink = false,
    TextInputType keyboardType = TextInputType.text,
  }) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0),
      child: Row(
        children: [
          Icon(icon, color: Theme.of(context).primaryColor),
          SizedBox(width: 16),
          Expanded(
            child: TextField(
              controller: controller,
              readOnly: !isEditing,
              keyboardType: keyboardType,
              decoration: InputDecoration(
                labelText: label,
                border: isEditing ? OutlineInputBorder() : InputBorder.none,
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildAvatarSection() {
    return Column(
      children: [
        SizedBox(height: 20),
        CircleAvatar(
          radius: 60,
          backgroundImage:
              NetworkImage('https://example.com/default-avatar.jpg'),
        ),
        SizedBox(height: 16),
        Text(
          "${nameController.text} ${surnameController.text}",
          style: TextStyle(
            fontSize: 24,
            fontWeight: FontWeight.bold,
            color: Theme.of(context).primaryColor,
          ),
        ),
        Text(
          programController.text,
          style: TextStyle(
            fontSize: 16,
            color: Theme.of(context).primaryColor,
          ),
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Student Profile'),
        actions: [
          IconButton(
            icon: Icon(isEditing ? Icons.save : Icons.edit),
            onPressed: _toggleEdit,
          ),
        ],
        elevation: 0,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            _buildAvatarSection(),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                children: [
                  _buildSectionTitle('Academic Information'),
                  _buildInfoTile(
                    label: 'Student ID',
                    controller: studentIDController,
                    icon: Icons.badge,
                  ),
                  _buildInfoTile(
                    label: 'Year of Study',
                    controller: studyYearController,
                    icon: Icons.school,
                  ),
                  _buildInfoTile(
                    label: 'Email',
                    controller: emailController,
                    icon: Icons.email,
                    keyboardType: TextInputType.emailAddress,
                  ),
                  _buildSectionTitle('Contact Information'),
                  _buildInfoTile(
                    label: 'Phone',
                    controller: phoneController,
                    icon: Icons.phone,
                    keyboardType: TextInputType.phone,
                  ),
                  _buildSectionTitle('Social Media'),
                  _buildInfoTile(
                    label: 'Instagram',
                    controller: instagramController,
                    icon: Icons.camera_alt,
                    isLink: true,
                  ),
                  _buildInfoTile(
                    label: 'LinkedIn',
                    controller: linkedinController,
                    icon: Icons.business,
                    isLink: true,
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
