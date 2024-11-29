import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../config/config.dart'; // AppConfig for API configuration
import './teacher_home_screen.dart'; // Import the home screen

class TeacherLoginPage extends StatefulWidget {
  @override
  _TeacherLoginPageState createState() => _TeacherLoginPageState();
}

class _TeacherLoginPageState extends State<TeacherLoginPage> {
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  bool isPasswordVisible = false;
  bool isLoading = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Teacher Login'),
        backgroundColor: Theme.of(context).primaryColor,
      ),
      body: Center(
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
                    'Login as a Teacher',
                    style: TextStyle(
                      fontSize: 24.0,
                      fontWeight: FontWeight.bold,
                      color: Theme.of(context).primaryColor,
                    ),
                  ),
                  SizedBox(height: 20.0),
                  _buildTextField(emailController, 'University Email',
                      Icons.email_outlined),
                  SizedBox(height: 20.0),
                  _buildPasswordField(),
                  SizedBox(height: 30.0),
                  ElevatedButton(
                    onPressed: isLoading ? null : () => _handleLogin(context),
                    style: ElevatedButton.styleFrom(
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10.0),
                      ),
                      padding: EdgeInsets.symmetric(vertical: 15.0),
                      backgroundColor: Theme.of(context).primaryColor,
                      foregroundColor: Colors.white,
                      textStyle: TextStyle(fontSize: 18.0),
                    ),
                    child: isLoading
                        ? CircularProgressIndicator(
                            color: Colors.white,
                          )
                        : Text('Login'),
                  ),
                  SizedBox(height: 20.0),
                  TextButton(
                    onPressed: () {
                      // Navigate back to registration
                      Navigator.pop(context);
                    },
                    child: Text(
                      'Don\'t have an account? Register',
                      style: TextStyle(
                        color: Theme.of(context).colorScheme.onBackground,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildTextField(
      TextEditingController controller, String label, IconData icon) {
    return TextField(
      controller: controller,
      decoration: InputDecoration(
        labelText: label,
        prefixIcon: Icon(icon, color: Theme.of(context).primaryColor),
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10.0),
        ),
      ),
    );
  }

  Widget _buildPasswordField() {
    return TextField(
      controller: passwordController,
      obscureText: !isPasswordVisible,
      decoration: InputDecoration(
        labelText: 'Password',
        prefixIcon:
            Icon(Icons.lock_outline, color: Theme.of(context).primaryColor),
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
        ),
      ),
    );
  }

  Future<void> _handleLogin(BuildContext context) async {
    if (emailController.text.isEmpty || passwordController.text.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Please fill in all the fields'),
          backgroundColor: Colors.red,
        ),
      );
      return;
    }

    setState(() {
      isLoading = true;
    });

    final success = await _loginTeacher(
      emailController.text,
      passwordController.text,
    );

    setState(() {
      isLoading = false;
    });

    if (success) {
      // Navigate to Home Screen
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => LinkasterHome()),
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Login failed. Check your credentials.'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }

  Future<bool> _loginTeacher(String email, String password) async {
    final url = Uri.parse('${AppConfig.apiBaseUrl}/api/auth/teacher/login');

    try {
      final response = await http.post(
        url,
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({
          "userEmail": email,
          "password": password,
        }),
      );

      if (response.statusCode == 200) {
        final responseData = jsonDecode(response.body);

        if (responseData['token'] != null) {
          // Save the token in SharedPreferences
          final prefs = await SharedPreferences.getInstance();
          await prefs.setString('authToken', responseData['token']);
          return true;
        }
      }
      return false;
    } catch (e) {
      print('Error during login: $e');
      return false;
    }
  }
}
