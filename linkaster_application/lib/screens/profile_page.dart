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
  late UserStatus currentStatus;

  bool isEditing = false;

  @override
  void initState() {
    super.initState();
    currentStatus = widget.status;
    initializeControllers(); // Initialize empty controllers
    fetchStudentData(); // Fetch data from the server
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
    final url = Uri.parse(
        '${AppConfig.apiBaseUrl}/api/student'); // Replace with your actual API URL

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
            name: data['name'] ?? '',
            surname: data['surname'] ?? '',
            studentID: data['studentID'] ?? '',
            studyYear: data['studyYear'] ?? '',
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

  @override
  void dispose() {
    nameController.dispose();
    surnameController.dispose();
    studentIDController.dispose();
    studyYearController.dispose();
    programController.dispose();
    emailController.dispose();
    instagramController.dispose();
    linkedinController.dispose();
    phoneController.dispose();
    super.dispose();
  }

  void _toggleEdit() {
    setState(() {
      isEditing = !isEditing;
      if (!isEditing) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Changes saved successfully!'),
            behavior: SnackBarBehavior.floating,
          ),
        );
      }
    });
  }

  Widget _buildSectionTitle(String title) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 12.0),
      child: Row(
        children: [
          Text(
            title,
            style: TextStyle(
              fontSize: 20,
              fontWeight: FontWeight.bold,
              color: Theme.of(context).primaryColor,
            ),
          ),
          SizedBox(width: 8),
          Expanded(
            child: Divider(
              color: Theme.of(context).primaryColor.withOpacity(0.3),
              thickness: 1,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildInfoTile({
    required String label,
    required TextEditingController controller,
    required IconData icon,
    TextInputType? keyboardType,
    bool isLink = false,
  }) {
    return Card(
      elevation: 0,
      color: isEditing ? Colors.white : Colors.grey.shade100,
      child: Padding(
        padding: const EdgeInsets.all(12.0),
        child: isEditing
            ? TextField(
                controller: controller,
                keyboardType: keyboardType,
                decoration: InputDecoration(
                  labelText: label,
                  prefixIcon: Icon(icon),
                  border: OutlineInputBorder(),
                  filled: true,
                  fillColor: Colors.white,
                ),
              )
            : ListTile(
                leading: Icon(icon, color: Theme.of(context).primaryColor),
                title: Text(label),
                subtitle: Text(
                  controller.text.isEmpty ? 'Not provided' : controller.text,
                  style: TextStyle(
                    fontSize: 16,
                    color: Colors.black87,
                    fontWeight: FontWeight.w500,
                  ),
                ),
              ),
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
