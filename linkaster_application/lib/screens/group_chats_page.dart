import 'package:flutter/material.dart';
import '../models/group_chat.dart';
import '../models/group_chat_info.dart';
import 'package:web_socket_channel/web_socket_channel.dart';
import 'package:web_socket_channel/status.dart' as status;
import 'package:web_socket_channel/io.dart';
import 'dart:convert';

class GroupChatPage extends StatefulWidget {
  final GroupChat chat;
  final GroupChatInfo chatInfo;

  String? token;

  GroupChatPage(
      {Key? key,
      required this.chat,
      required this.chatInfo,
      required this.token})
      : super(key: key);

  @override
  _GroupChatPageState createState() => _GroupChatPageState();
}

class _GroupChatPageState extends State<GroupChatPage> {
  late TextEditingController moduleNameController;
  late TextEditingController moduleTimeController;
  late TextEditingController moduleCodeController;
  bool isEditing = false;
  List<String> editedStudentsList = [];
  List<String> editedTeachersList = [];
  List<Map<String, dynamic>> messages = [
    {
      'name': 'John Doe',
      'message': 'Hello, everyone!',
      'time': '10:00 AM',
      'isSent': true,
    },
    {
      'name': 'Jane Smith',
      'message': 'Hi John!',
      'time': '10:05 AM',
      'isSent': false,
    },
    // Add more messages as needed
  ];

  @override
  void initState() {
    print('PR Token: ${widget.token}');
    super.initState();
    moduleNameController =
        TextEditingController(text: widget.chatInfo.moduleName);
    moduleTimeController =
        TextEditingController(text: widget.chatInfo.moduleTime);
    moduleCodeController =
        TextEditingController(text: widget.chatInfo.moduleCode);
    editedStudentsList = List.from(widget.chatInfo.studentsList);
    editedTeachersList = List.from(widget.chatInfo.teachersList);
  }

  @override
  void dispose() {
    moduleNameController.dispose();
    moduleTimeController.dispose();
    moduleCodeController.dispose();
    super.dispose();
  }

  void _showModuleInfo() {
    // Reset controllers to current values when opening dialog
    moduleNameController.text = widget.chatInfo.moduleName;
    moduleTimeController.text = widget.chatInfo.moduleTime;
    moduleCodeController.text = widget.chatInfo.moduleCode;
    editedStudentsList = List.from(widget.chatInfo.studentsList);
    editedTeachersList = List.from(widget.chatInfo.teachersList);
    isEditing = false;

    showDialog(
      context: context,
      builder: (BuildContext context) => StatefulBuilder(
        builder: (context, setState) => Dialog(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(16),
          ),
          elevation: 0,
          backgroundColor: Colors.transparent,
          child: Container(
            padding: EdgeInsets.all(20),
            decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.circular(16),
              boxShadow: [
                BoxShadow(
                  color: Colors.black26,
                  blurRadius: 10.0,
                  offset: Offset(0.0, 10.0),
                ),
              ],
            ),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                // Header
                Row(
                  children: [
                    Icon(
                      Icons.school,
                      color: Theme.of(context).primaryColor,
                      size: 28,
                    ),
                    SizedBox(width: 10),
                    Expanded(
                      child: Text(
                        widget.chat.chatname,
                        style: TextStyle(
                          fontSize: 24,
                          fontWeight: FontWeight.bold,
                          color: Theme.of(context).primaryColor,
                        ),
                      ),
                    ),
                    IconButton(
                      icon: Icon(
                        isEditing ? Icons.done : Icons.edit,
                        color: Colors.lightBlueAccent,
                      ),
                      onPressed: () {
                        setState(() {
                          isEditing = !isEditing;
                          if (!isEditing) {
                            // Here you would typically save the changes
                            // For now, we'll just print the new values
                            print(
                                'New Module Name: ${moduleNameController.text}');
                            print(
                                'New Module Time: ${moduleTimeController.text}');
                            print(
                                'New Module Code: ${moduleCodeController.text}');
                            print('New Students List: $editedStudentsList');
                            print('New Teachers List: $editedTeachersList');
                          }
                        });
                      },
                    ),
                    IconButton(
                      icon: Icon(Icons.close, color: Colors.grey[600]),
                      onPressed: () => Navigator.pop(context),
                    ),
                  ],
                ),
                Divider(height: 20),

                // Scrollable content
                SingleChildScrollView(
                  child: Column(
                    children: [
                      // Module Information
                      _buildEditableInfoRow(
                        setState,
                        Icons.book,
                        'Module Name',
                        moduleNameController,
                      ),
                      _buildEditableInfoRow(
                        setState,
                        Icons.access_time,
                        'Time',
                        moduleTimeController,
                      ),
                      _buildEditableInfoRow(
                        setState,
                        Icons.code,
                        'Module Code',
                        moduleCodeController,
                      ),

                      // Students Section
                      _buildEditableListSection(
                        setState,
                        Icons.people,
                        'Students',
                        editedStudentsList,
                      ),

                      // Teachers Section
                      _buildEditableListSection(
                        setState,
                        Icons.person,
                        'Teachers',
                        editedTeachersList,
                      ),
                    ],
                  ),
                ),

                SizedBox(height: 20),

                // Save/Close Button
                TextButton(
                  style: TextButton.styleFrom(
                    padding: EdgeInsets.symmetric(horizontal: 40, vertical: 15),
                    backgroundColor: Colors.lightBlueAccent,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(30),
                    ),
                  ),
                  child: Text(
                    isEditing ? 'Save Changes' : 'Close',
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  onPressed: () {
                    if (isEditing) {
                      setState(() {
                        isEditing = false;
                        // Here you would typically save the changes
                      });
                    } else {
                      Navigator.pop(context);
                    }
                  },
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildEditableInfoRow(
    StateSetter setState,
    IconData icon,
    String label,
    TextEditingController controller,
  ) {
    return Padding(
      padding: EdgeInsets.symmetric(vertical: 8),
      child: Row(
        children: [
          Icon(
            icon,
            size: 20,
            color: Colors.lightBlueAccent,
          ),
          SizedBox(width: 12),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  label,
                  style: TextStyle(
                    fontSize: 12,
                    color: Colors.grey[600],
                  ),
                ),
                if (isEditing)
                  TextField(
                    controller: controller,
                    decoration: InputDecoration(
                      isDense: true,
                      contentPadding: EdgeInsets.symmetric(vertical: 8),
                    ),
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w500,
                    ),
                  )
                else
                  Text(
                    controller.text,
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w500,
                    ),
                  ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildEditableListSection(
    StateSetter setState,
    IconData icon,
    String title,
    List<String> items,
  ) {
    return Container(
      margin: EdgeInsets.symmetric(vertical: 8),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              Icon(
                icon,
                size: 20,
                color: Colors.lightBlueAccent,
              ),
              SizedBox(width: 12),
              Text(
                title,
                style: TextStyle(
                  fontSize: 12,
                  color: Colors.grey[600],
                ),
              ),
              if (isEditing) ...[
                Spacer(),
                IconButton(
                  icon: Icon(Icons.add, size: 20),
                  color: Colors.lightBlueAccent,
                  onPressed: () {
                    final TextEditingController newItemController =
                        TextEditingController();
                    showDialog(
                      context: context,
                      builder: (context) => AlertDialog(
                        title: Text(
                            'Add New ${title.substring(0, title.length - 1)}'),
                        content: TextField(
                          controller: newItemController,
                          decoration: InputDecoration(
                            hintText: 'Enter name',
                          ),
                        ),
                        actions: [
                          TextButton(
                            child: Text('Cancel'),
                            onPressed: () => Navigator.pop(context),
                          ),
                          TextButton(
                            child: Text('Add'),
                            onPressed: () {
                              if (newItemController.text.isNotEmpty) {
                                setState(() {
                                  items.add(newItemController.text);
                                });
                                Navigator.pop(context);
                              }
                            },
                          ),
                        ],
                      ),
                    );
                  },
                ),
              ],
            ],
          ),
          Container(
            margin: EdgeInsets.only(left: 32, top: 8),
            child: Wrap(
              spacing: 8,
              runSpacing: 8,
              children: items
                  .map((item) => Container(
                        padding:
                            EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                        decoration: BoxDecoration(
                          color: Colors.grey[100],
                          borderRadius: BorderRadius.circular(20),
                          border: Border.all(
                            color: Colors.lightBlueAccent.withOpacity(0.3),
                          ),
                        ),
                        child: Row(
                          mainAxisSize: MainAxisSize.min,
                          children: [
                            Text(
                              item,
                              style: TextStyle(
                                fontSize: 12,
                                color: Colors.grey[800],
                              ),
                            ),
                            if (isEditing) ...[
                              SizedBox(width: 4),
                              InkWell(
                                onTap: () {
                                  setState(() {
                                    items.remove(item);
                                  });
                                },
                                child: Icon(
                                  Icons.close,
                                  size: 16,
                                  color: Colors.grey[600],
                                ),
                              ),
                            ],
                          ],
                        ),
                      ))
                  .toList(),
            ),
          ),
        ],
      ),
    );
  }

  // Rest of the GroupChatPage implementation remains the same...
  // (Keep all the existing build method and message-related code)

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: GestureDetector(
          onTap: _showModuleInfo,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                children: [
                  Text(widget.chat.chatname),
                  SizedBox(width: 4),
                  Icon(
                    Icons.info_outline,
                    size: 20,
                  ),
                ],
              ),
              Text(
                '${widget.chat.name} • ${widget.chat.time}',
                style: TextStyle(
                  fontSize: 12,
                  fontWeight: FontWeight.normal,
                ),
              ),
            ],
          ),
        ),
        backgroundColor: Theme.of(context).primaryColor,
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              padding: EdgeInsets.symmetric(vertical: 10, horizontal: 8),
              itemCount: messages.length,
              itemBuilder: (context, index) {
                final message = messages[index];
                return Align(
                  alignment: message['isSent']
                      ? Alignment.centerRight
                      : Alignment.centerLeft,
                  child: Container(
                    margin: EdgeInsets.symmetric(vertical: 4, horizontal: 12),
                    padding: EdgeInsets.symmetric(vertical: 10, horizontal: 14),
                    constraints: BoxConstraints(maxWidth: 250),
                    decoration: BoxDecoration(
                      color: message['isSent']
                          ? Colors.lightBlueAccent
                          : Colors.grey[200],
                      borderRadius: BorderRadius.only(
                        topLeft: Radius.circular(16),
                        topRight: Radius.circular(16),
                        bottomLeft: message['isSent']
                            ? Radius.circular(16)
                            : Radius.circular(0),
                        bottomRight: message['isSent']
                            ? Radius.circular(0)
                            : Radius.circular(16),
                      ),
                    ),
                    child: Column(
                      crossAxisAlignment: message['isSent']
                          ? CrossAxisAlignment.end
                          : CrossAxisAlignment.start,
                      children: [
                        Text(
                          message['name'],
                          style: TextStyle(fontWeight: FontWeight.bold),
                        ),
                        SizedBox(height: 4),
                        Text(
                          message['message'],
                          style: TextStyle(fontSize: 16),
                        ),
                        SizedBox(height: 4),
                        Text(
                          message['time'],
                          style: TextStyle(fontSize: 12, color: Colors.grey),
                        ),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),
          _buildMessageInputField(context),
        ],
      ),
    );
  }

  Widget _buildMessageInputField(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(8.0),
      decoration: BoxDecoration(
        color: Colors.white,
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.1),
            spreadRadius: 1,
            blurRadius: 3,
            offset: Offset(0, -1),
          ),
        ],
      ),
      child: Row(
        children: [
          Expanded(
            child: TextField(
              decoration: InputDecoration(
                hintText: 'Type a message...',
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(30),
                ),
                contentPadding:
                    EdgeInsets.symmetric(horizontal: 20, vertical: 10),
              ),
            ),
          ),
          SizedBox(width: 8),
          IconButton(
            icon: Icon(Icons.attach_file),
            color: Theme.of(context).primaryColor,
            onPressed: () {
              // Add your file attachment logic here
            },
          ),
          IconButton(
            icon: Icon(Icons.send),
            color: Theme.of(context).primaryColor,
            onPressed: () {
              // Add your send message logic here
            },
          ),
        ],
      ),
    );
  }
}
