import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import '../config/config.dart';

class JoinModuleDialog extends StatefulWidget {
  final String? token;

  const JoinModuleDialog({Key? key, this.token}) : super(key: key);

  @override
  _JoinModuleDialogState createState() => _JoinModuleDialogState();
}

class _JoinModuleDialogState extends State<JoinModuleDialog> {
  final TextEditingController _moduleCodeController = TextEditingController();
  bool _isLoading = false;

  Future<void> _joinModule() async {
    final moduleCode = _moduleCodeController.text.trim();

    if (moduleCode.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Please enter a module code')),
      );
      return;
    }

    setState(() {
      _isLoading = true;
    });

    final response = await http.post(
      Uri.parse('${AppConfig.apiBaseUrl}/api/module/join'), // Change as needed
      headers: {
        'Authorization': 'Bearer ${widget.token}',
        'Content-Type': 'application/json',
      },
      body: '{"code": "$moduleCode"}',
    );

    setState(() {
      _isLoading = false;
    });

    if (response.statusCode == 200) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Successfully joined the module!')),
      );
      Navigator.pop(context);
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Failed to join the module')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text('Join Module'),
      content: TextField(
        controller: _moduleCodeController,
        decoration: InputDecoration(
          labelText: 'Module Code',
          border: OutlineInputBorder(),
        ),
      ),
      actions: [
        TextButton(
          onPressed: () => Navigator.pop(context),
          child: Text('Cancel'),
        ),
        TextButton(
          onPressed: _isLoading ? null : _joinModule,
          child: _isLoading ? CircularProgressIndicator() : Text('Join'),
        ),
      ],
    );
  }
}
