import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../config/config.dart';

class CreateModuleDialog extends StatefulWidget {
  final String? token; // Accept the token from HomeScreen

  CreateModuleDialog({required this.token});

  @override
  _CreateModuleDialogState createState() => _CreateModuleDialogState();
}

class _CreateModuleDialogState extends State<CreateModuleDialog> {
  final TextEditingController _moduleNameController = TextEditingController();
  final TextEditingController _moduleCodeController = TextEditingController();

  int _currentStep = 0;
  String? moduleId; // Store created module ID

  // Event Details Controllers
  final List<Map<String, TextEditingController>> _eventControllers =
      List.generate(
    2,
    (index) => {
      'name': TextEditingController(),
      'location': TextEditingController(),
      'startDate': TextEditingController(),
      'startTime': TextEditingController(),
      'endTime': TextEditingController(),
    },
  );

  @override
  Widget build(BuildContext context) {
    return Dialog(
      child: Container(
        constraints: BoxConstraints(
          maxHeight: MediaQuery.of(context).size.height * 0.8,
          maxWidth: MediaQuery.of(context).size.width * 0.85,
        ),
        child: SingleChildScrollView(
          child: Column(
            children: [
              SizedBox(
                width: MediaQuery.of(context).size.width * 0.85,
                child: Stepper(
                  currentStep: _currentStep,
                  onStepContinue: _onStepContinue,
                  onStepCancel: _onStepCancel,
                  physics: ClampingScrollPhysics(),
                  steps: _buildSteps(),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  /// Handles the Continue button click
  void _onStepContinue() async {
    if (_currentStep == 0) {
      await _sendModuleInfoRequest();
    } else {
      final isSuccess = await _sendEventDetailsRequest(
          _currentStep - 1); // Send the event request
      if (!isSuccess) {
        return; // Stop if event submission failed
      }
    }

    if (_currentStep < _buildSteps().length - 1) {
      setState(() {
        _currentStep++;
      });
    } else {
      Navigator.of(context).pop(); // Close dialog after final step
    }
  }

  /// Handles the Cancel button click
  void _onStepCancel() {
    Navigator.of(context).pop(); // Close the dialog
  }

  /// Send Module Info Request
  Future<void> _sendModuleInfoRequest() async {
    final response = await http.post(
      Uri.parse('${AppConfig.apiBaseUrl}/api/module/create'),
      body: json.encode({
        'moduleOwnerName': 'GOD',
        'type': 'class_module',
        'moduleName': _moduleNameController.text,
        'moduleCode': _moduleCodeController.text,
      }),
      headers: {
        'Authorization': 'Bearer ${widget.token}',
        'Content-Type': 'application/json',
      },
    );

    if (response.statusCode == 201) {
      final Map<String, dynamic> responseData = json.decode(response.body);
      moduleId = responseData['moduleId'];
      print('Module created successfully! Module ID: $moduleId');
    } else {
      print('Failed to create module');
    }
  }

  /// Send Event Details Request
  Future<bool> _sendEventDetailsRequest(int eventIndex) async {
    if (moduleId == null) {
      print('Module ID not found!');
      return false;
    }

    final response = await http.post(
      Uri.parse('${AppConfig.apiBaseUrl}/api/module/event'),
      body: json.encode({
        'name': _eventControllers[eventIndex]['name']!.text,
        'startTime': _eventControllers[eventIndex]['startTime']!.text,
        'endTime': _eventControllers[eventIndex]['endTime']!.text,
        'room': _eventControllers[eventIndex]['location']!.text,
        'date': _eventControllers[eventIndex]['startDate']!.text,
        'moduleId': moduleId,
      }),
      headers: {
        'Authorization': 'Bearer ${widget.token}',
        'Content-Type': 'application/json',
      },
    );

    if (response.statusCode == 201) {
      print('Event ${eventIndex + 1} created successfully!');
      return true;
    } else {
      print('Failed to create event ${eventIndex + 1}');
      return false;
    }
  }

  List<Step> _buildSteps() {
    List<Step> steps = [
      Step(
        title: Text('Module Info'),
        content: Column(
          children: [
            TextField(
              controller: _moduleNameController,
              decoration: InputDecoration(labelText: 'Module Name'),
            ),
            TextField(
              controller: _moduleCodeController,
              decoration: InputDecoration(labelText: 'Module Code'),
            ),
          ],
        ),
      ),
    ];

    for (int i = 0; i < 2; i++) {
      steps.add(
        Step(
          title: Text('Event ${i + 1} Info'),
          content: Column(
            children: [
              TextField(
                controller: _eventControllers[i]['name'],
                decoration: InputDecoration(labelText: 'Event Name'),
              ),
              TextField(
                controller: _eventControllers[i]['location'],
                decoration: InputDecoration(labelText: 'Location'),
              ),
              TextField(
                controller: _eventControllers[i]['startDate'],
                decoration:
                    InputDecoration(labelText: 'Start Date (yyyy-MM-dd)'),
              ),
              TextField(
                controller: _eventControllers[i]['startTime'],
                decoration: InputDecoration(labelText: 'Start Time'),
              ),
              TextField(
                controller: _eventControllers[i]['endTime'],
                decoration: InputDecoration(labelText: 'End Time'),
              ),
              if (i == 0) // Buttons in first event
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    ElevatedButton(
                      onPressed: () async {
                        final success = await _sendEventDetailsRequest(0);
                        if (success) {
                          setState(() => _currentStep++);
                        }
                      },
                      child: Text('Continue to Next Event'),
                    ),
                    ElevatedButton(
                      onPressed: _onStepCancel,
                      child: Text('Cancel'),
                    ),
                    ElevatedButton(
                      onPressed: () async {
                        final success = await _sendEventDetailsRequest(0);
                        if (success) {
                          Navigator.of(context).pop();
                        }
                      },
                      child: Text('Finish'),
                    ),
                  ],
                ),
            ],
          ),
        ),
      );
    }

    return steps;
  }
}
