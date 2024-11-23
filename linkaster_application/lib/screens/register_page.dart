import 'package:flutter/material.dart';
import 'logIn_page.dart'; // Import login page
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../screensTeacher/teacher_register_page.dart'; // Import teacher registration page

class RegisterPage extends StatefulWidget {
  @override
  _RegisterPageState createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  final TextEditingController nameController = TextEditingController();
  final TextEditingController surnameController = TextEditingController();
  final TextEditingController idController = TextEditingController();
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  String? selectedProgram;
  String? selectedYear;
  bool isPasswordVisible = false;

  final List<String> studyPrograms = [
    'Software Engineering',
    'Computer Science',
    'Finance and Accounts',
    'Business Management',
    'Business Management with Business Analytics',
    'Business Management with Finance',
    'Business Management and Media',
    'Cyber Security',
    'Data Science',
    'Logistics and Supply Chain Management',
    'Management',
  ];

  final List<String> studyYears = ['Foundation', '1st', '2nd', '3rd', 'Master'];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          _buildBackground(context),
          _buildRegisterForm(context),
        ],
      ),
    );
  }

  Widget _buildBackground(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        gradient: LinearGradient(
          colors: [
            Theme.of(context).primaryColor,
            Theme.of(context).colorScheme.secondary,
          ],
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
        ),
      ),
    );
  }

  Widget _buildRegisterForm(BuildContext context) {
    return Center(
      child: SingleChildScrollView(
        padding: const EdgeInsets.all(20.0),
        child: Card(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(15.0),
          ),
          elevation: 8.0,
          child: Padding(
            padding: const EdgeInsets.all(20.0),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                Text(
                  'Register Account',
                  style: TextStyle(
                    fontSize: 24.0,
                    fontWeight: FontWeight.bold,
                    color: Theme.of(context).primaryColor,
                  ),
                ),
                SizedBox(height: 20.0),
                _buildTextField(nameController, 'Name', Icons.person, context),
                SizedBox(height: 20.0),
                _buildTextField(surnameController, 'Surname',
                    Icons.person_outline, context),
                SizedBox(height: 20.0),
                _buildTextField(idController, 'ID', Icons.badge, context),
                SizedBox(height: 20.0),
                _buildYearDropdown(context),
                SizedBox(height: 20.0),
                _buildTextField(
                    emailController, 'University Email', Icons.email, context),
                SizedBox(height: 20.0),
                _buildPasswordField(context),
                SizedBox(height: 20.0),
                _buildProgramDropdown(context),
                SizedBox(height: 20.0),
                ElevatedButton(
                  onPressed: () {
                    _handleRegister(context);
                  },
                  style: ElevatedButton.styleFrom(
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                    padding: EdgeInsets.symmetric(vertical: 15.0),
                    backgroundColor: Theme.of(context).primaryColor,
                    foregroundColor: Colors.white,
                    textStyle: TextStyle(fontSize: 18.0),
                  ),
                  child: Text('Register'),
                ),
                SizedBox(height: 10.0),
                TextButton(
                  onPressed: () {
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                        builder: (context) => LoginPage(),
                      ),
                    );
                  },
                  child: Text(
                    'Already have an account? Login',
                    style: TextStyle(
                        color: Theme.of(context).colorScheme.onBackground),
                  ),
                ),
                SizedBox(height: 30.0),
                // Add Teacher Registration button
                TextButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => TeacherRegistrationPage(),
                      ),
                    );
                  },
                  child: Text(
                    'Are you a teacher? Teacher registration',
                    style: TextStyle(
                      color: Colors.blue,
                      fontWeight: FontWeight.bold,
                      decoration: TextDecoration.underline,
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildTextField(TextEditingController controller, String label,
      IconData icon, BuildContext context) {
    return TextField(
      controller: controller,
      decoration: InputDecoration(
        labelText: label,
        labelStyle:
            TextStyle(color: Theme.of(context).colorScheme.onBackground),
        prefixIcon: Icon(icon, color: Theme.of(context).primaryColor),
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10.0),
          borderSide: BorderSide(color: Theme.of(context).primaryColor),
        ),
      ),
    );
  }

  Widget _buildPasswordField(BuildContext context) {
    return TextField(
      controller: passwordController,
      obscureText: !isPasswordVisible,
      decoration: InputDecoration(
        labelText: 'Password',
        labelStyle:
            TextStyle(color: Theme.of(context).colorScheme.onBackground),
        prefixIcon: Icon(Icons.lock, color: Theme.of(context).primaryColor),
        suffixIcon: IconButton(
          icon: Icon(
            isPasswordVisible ? Icons.visibility : Icons.visibility_off,
            color: Theme.of(context).primaryColor,
          ),
          onPressed: () {
            setState(() {
              isPasswordVisible = !isPasswordVisible;
            });
          },
        ),
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10.0),
          borderSide: BorderSide(color: Theme.of(context).primaryColor),
        ),
      ),
    );
  }

  Widget _buildProgramDropdown(BuildContext context) {
    return DropdownButtonFormField<String>(
      value: selectedProgram,
      onChanged: (newValue) {
        setState(() {
          selectedProgram = newValue;
        });
      },
      decoration: InputDecoration(
        labelText: 'Select Study Program',
        labelStyle:
            TextStyle(color: Theme.of(context).colorScheme.onBackground),
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
      ),
      items: studyPrograms
          .map((program) =>
              DropdownMenuItem(value: program, child: Text(program)))
          .toList(),
    );
  }

  Widget _buildYearDropdown(BuildContext context) {
    return DropdownButtonFormField<String>(
      value: selectedYear,
      onChanged: (newValue) {
        setState(() {
          selectedYear = newValue;
        });
      },
      decoration: InputDecoration(
        labelText: 'Select Year',
        labelStyle:
            TextStyle(color: Theme.of(context).colorScheme.onBackground),
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
      ),
      items: studyYears
          .map((year) => DropdownMenuItem(value: year, child: Text(year)))
          .toList(),
    );
  }

  void _handleRegister(BuildContext context) {
    if (nameController.text.isEmpty ||
        surnameController.text.isEmpty ||
        idController.text.isEmpty ||
        emailController.text.isEmpty ||
        passwordController.text.isEmpty ||
        selectedProgram == null ||
        selectedYear == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
            content: Text('Please fill in all the fields'),
            backgroundColor: Colors.red),
      );
    } else if (passwordController.text.length < 6) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
            content: Text('Password must be at least 6 characters long'),
            backgroundColor: Colors.orange),
      );
    } else {
      // POST request to register user

      Navigator.pushReplacement(
          context, MaterialPageRoute(builder: (context) => LoginPage()));
    }
  }

  Future<bool> registerUser(
    String name,
    String surname,
    String id,
    String email,
    String password,
    String program,
    String year,
  ) async {
    final url = Uri.parse('http://localhost:8080/api/auth/student/register');
    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'name': name,
        'surname': surname,
        'studentId': id,
        'email': email,
        'password': password,
        'studyProg': program,
        'year': year,
        'subject': "",
      }),
    );

    return response.statusCode == 200;
  }
}
