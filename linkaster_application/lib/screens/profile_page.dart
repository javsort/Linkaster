import 'package:flutter/material.dart';
import '../widget/user_status.dart';

class ProfilePage extends StatefulWidget {
  final String name;
  final String surname;
  final String studentID;
  final String studyYear;
  final String program;
  final String email;
  final String avatar;
  final UserStatus status;
  // Optional fields
  final String? instagram;
  final String? linkedin;
  final String? phone;

  const ProfilePage({
    Key? key,
    required this.name,
    required this.surname,
    required this.studentID,
    required this.studyYear,
    required this.program,
    required this.email,
    required this.status,
    this.avatar = 'https://example.com/default-avatar.jpg',
    this.instagram,
    this.linkedin,
    this.phone,
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
    initializeControllers();
    currentStatus = widget.status;
  }

  void initializeControllers() {
    nameController = TextEditingController(text: widget.name);
    surnameController = TextEditingController(text: widget.surname);
    studentIDController = TextEditingController(text: widget.studentID);
    studyYearController = TextEditingController(text: widget.studyYear);
    programController = TextEditingController(text: widget.program);
    emailController = TextEditingController(text: widget.email);
    instagramController = TextEditingController(text: widget.instagram ?? '');
    linkedinController = TextEditingController(text: widget.linkedin ?? '');
    phoneController = TextEditingController(text: widget.phone ?? '');
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

  void _toggleStatus() {
    setState(() {
      currentStatus = currentStatus == UserStatus.available
          ? UserStatus.busy
          : UserStatus.available;
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

  Widget _buildAvatarSection() {
    return Container(
      width: double.infinity,
      decoration: BoxDecoration(
        gradient: LinearGradient(
          begin: Alignment.topCenter,
          end: Alignment.bottomCenter,
          colors: [
            Colors.white,
            Theme.of(context).primaryColor.withOpacity(0.2),
          ],
        ),
      ),
      child: Column(
        children: [
          SizedBox(height: 20),
          Stack(
            children: [
              CircleAvatar(
                radius: 60,
                backgroundColor: Colors.white,
                child: CircleAvatar(
                  radius: 57,
                  backgroundImage: NetworkImage(widget.avatar),
                ),
              ),
              Positioned(
                right: 0,
                bottom: 0,
                child: GestureDetector(
                  onTap: isEditing ? _toggleStatus : null,
                  child: Container(
                    decoration: BoxDecoration(
                      shape: BoxShape.circle,
                      color: Colors.white,
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black.withOpacity(0.2),
                          spreadRadius: 1,
                          blurRadius: 3,
                        ),
                      ],
                    ),
                    padding: EdgeInsets.all(3),
                    child: StatusIndicator(
                      status: currentStatus,
                      size: 20,
                      withAnimation: !isEditing,
                    ),
                  ),
                ),
              ),
              if (isEditing)
                Positioned(
                  right: 0,
                  top: 0,
                  child: CircleAvatar(
                    backgroundColor: Colors.white,
                    radius: 18,
                    child: IconButton(
                      icon: Icon(Icons.camera_alt,
                          size: 18, color: Theme.of(context).primaryColor),
                      onPressed: () {
                        // TODO: Implement image picker
                      },
                    ),
                  ),
                ),
            ],
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
          SizedBox(height: 20),
        ],
      ),
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
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _buildAvatarSection(),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
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
            if (isEditing)
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    ElevatedButton.icon(
                      onPressed: () {
                        setState(() {
                          isEditing = false;
                          initializeControllers();
                          currentStatus = widget.status;
                        });
                      },
                      icon: Icon(Icons.close),
                      label: Text('Cancel'),
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.grey,
                        padding:
                            EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                      ),
                    ),
                    ElevatedButton.icon(
                      onPressed: _toggleEdit,
                      icon: Icon(Icons.check),
                      label: Text('Save Changes'),
                      style: ElevatedButton.styleFrom(
                        padding:
                            EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                      ),
                    ),
                  ],
                ),
              ),
          ],
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
}
