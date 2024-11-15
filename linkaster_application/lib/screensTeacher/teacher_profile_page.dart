import 'package:flutter/material.dart';
import '../widget/user_status.dart';

class TeacherProfile extends StatefulWidget {
  final String id;
  final String email;
  final String name;
  final String surname;
  final UserStatus status;
  final String? linkedin;
  final String? github;

  const TeacherProfile({
    Key? key,
    required this.id,
    required this.email,
    required this.name,
    required this.surname,
    required this.status,
    this.linkedin,
    this.github,
  }) : super(key: key);

  @override
  _TeacherProfilePageState createState() => _TeacherProfilePageState();
}

class _TeacherProfilePageState extends State<TeacherProfile> {
  late TextEditingController idController;
  late TextEditingController emailController;
  late TextEditingController nameController;
  late TextEditingController surnameController;
  late TextEditingController linkedinController;
  late TextEditingController githubController;
  late UserStatus currentStatus;

  bool isEditing = false;

  @override
  void initState() {
    super.initState();
    initializeControllers();
    currentStatus = widget.status;
  }

  void initializeControllers() {
    idController = TextEditingController(text: widget.id);
    emailController = TextEditingController(text: widget.email);
    nameController = TextEditingController(text: widget.name);
    surnameController = TextEditingController(text: widget.surname);
    linkedinController = TextEditingController(text: widget.linkedin ?? '');
    githubController = TextEditingController(text: widget.github ?? '');
  }

  @override
  void dispose() {
    idController.dispose();
    emailController.dispose();
    nameController.dispose();
    surnameController.dispose();
    linkedinController.dispose();
    githubController.dispose();
    super.dispose();
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
                title: Text(label,
                    style: TextStyle(
                      fontSize: 14,
                      color: Colors.grey.shade600,
                    )),
                subtitle: Text(
                  controller.text.isEmpty ? 'Not provided' : controller.text,
                  style: TextStyle(
                    fontSize: 16,
                    color: Colors.black87,
                    fontWeight: FontWeight.w500,
                  ),
                ),
                dense: true,
              ),
      ),
    );
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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Teacher Profile'),
        actions: [
          IconButton(
            icon: Icon(isEditing ? Icons.save : Icons.edit),
            onPressed: _toggleEdit,
          ),
        ],
        elevation: 0,
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              _buildSectionTitle('Teacher Information'),
              _buildInfoTile(
                label: 'Teacher ID',
                controller: idController,
                icon: Icons.badge,
              ),
              _buildInfoTile(
                label: 'Email',
                controller: emailController,
                icon: Icons.email,
                keyboardType: TextInputType.emailAddress,
              ),
              _buildInfoTile(
                label: 'Name',
                controller: nameController,
                icon: Icons.person,
              ),
              _buildInfoTile(
                label: 'Surname',
                controller: surnameController,
                icon: Icons.person_outline,
              ),
              _buildSectionTitle('Social Media'),
              _buildInfoTile(
                label: 'LinkedIn',
                controller: linkedinController,
                icon: Icons.business,
                isLink: true,
              ),
              _buildInfoTile(
                label: 'GitHub',
                controller: githubController,
                icon: Icons.code,
                isLink: true,
              ),
              if (isEditing)
                Padding(
                  padding: const EdgeInsets.symmetric(vertical: 16.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      ElevatedButton.icon(
                        onPressed: () {
                          setState(() {
                            isEditing = false;
                            initializeControllers();
                          });
                        },
                        icon: Icon(Icons.close),
                        label: Text('Cancel'),
                        style: ElevatedButton.styleFrom(
                          backgroundColor: Colors.grey,
                          padding: EdgeInsets.symmetric(
                              horizontal: 24, vertical: 12),
                        ),
                      ),
                      ElevatedButton.icon(
                        onPressed: _toggleEdit,
                        icon: Icon(Icons.check),
                        label: Text('Save Changes'),
                        style: ElevatedButton.styleFrom(
                          padding: EdgeInsets.symmetric(
                              horizontal: 24, vertical: 12),
                        ),
                      ),
                    ],
                  ),
                ),
            ],
          ),
        ),
      ),
    );
  }
}
