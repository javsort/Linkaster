import 'package:flutter/material.dart';
import 'package:table_calendar/table_calendar.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../models/module.dart';

class TimetablePage extends StatefulWidget {
  String? token;

  TimetablePage({required this.token});

  @override
  _TimetablePageState createState() => _TimetablePageState();
}

class _TimetablePageState extends State<TimetablePage> {
  DateTime _selectedDay = DateTime.now(); // Currently selected day
  CalendarFormat _calendarFormat =
      CalendarFormat.week; // Initial calendar format
  String? token; // Token to be retrieved

  final Map<DateTime, List<Module>> _classData = {
    DateTime(2024, 10, 29): [
      Module(
        subject: "Internet Applications Engineering",
        time: "15:00 PM - 17:00 PM",
        room: "706",
        instructor: "Prof. David Georg Reichelt",
        isMandatory: true, // Mandatory class
      ),
    ],
    DateTime(2024, 10, 30): [
      Module(
        subject: "German Language B1",
        time: "16:00 PM - 18:00 PM",
        room: "706",
        instructor: "Prof. Barbara Osnowski",
        isMandatory: false, // Non-mandatory class
      ),
    ],
  };

  @override
  void initState() {
    super.initState();
    _retrieveToken();
  }

  /// Retrieve token from SharedPreferences
  Future<void> _retrieveToken() async {
    final prefs = await SharedPreferences.getInstance();
    setState(() {
      token = prefs.getString('authToken');
      print('Retrieved token: $token');
    });

    // Fetch timetable data based on the token
    if (token != null) {
      _fetchTimetableData();
    }
  }

  /// Simulate fetching timetable data based on the token
  Future<void> _fetchTimetableData() async {
    // Simulate an API call or database fetch
    print('Fetching timetable data with token: $token');

    // Example: Add new classes dynamically from "API response"
    setState(() {
      _classData[DateTime(2024, 11, 2)] = [
        Module(
          subject: "Advanced Flutter",
          time: "10:00 AM - 12:00 PM",
          room: "701",
          instructor: "Dr. John Doe",
          isMandatory: true,
        ),
      ];
    });
  }

  DateTime normalizeDate(DateTime date) {
    return DateTime(date.year, date.month, date.day); // Normalize to midnight
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Timetable'),
      ),
      body: Column(
        children: [
          TableCalendar(
            firstDay: DateTime.utc(2020, 10, 16),
            lastDay: DateTime.utc(2030, 10, 16),
            focusedDay: _selectedDay,
            selectedDayPredicate: (day) => isSameDay(day, _selectedDay),
            calendarFormat: _calendarFormat,
            onDaySelected: (selectedDay, focusedDay) {
              setState(() {
                _selectedDay = selectedDay;
              });
            },
            onFormatChanged: (format) {
              setState(() {
                _calendarFormat = format;
              });
            },
          ),
          const SizedBox(height: 16),
          _buildDailyClassList(),
          _buildNavigationButtons(),
        ],
      ),
    );
  }

  /// Build the list of classes for the selected day
  Widget _buildDailyClassList() {
    final normalizedDate = normalizeDate(_selectedDay);
    final classes = _classData[normalizedDate] ?? [];

    if (classes.isEmpty) {
      return Center(
        child: Text(
          'No classes scheduled for ${normalizedDate.toLocal().toString().split(' ')[0]}.',
        ),
      );
    }

    return Expanded(
      child: ListView.builder(
        itemCount: classes.length,
        itemBuilder: (context, index) {
          final classItem = classes[index];
          return Card(
            margin: EdgeInsets.symmetric(vertical: 4, horizontal: 16),
            child: ListTile(
              leading: Container(
                width: 10,
                height: 10,
                decoration: BoxDecoration(
                  color: classItem.isMandatory ? Colors.red : Colors.orange,
                  shape: BoxShape.circle,
                ),
              ),
              title: Text(
                classItem.subject,
                style: TextStyle(fontWeight: FontWeight.bold),
              ),
              subtitle: Text(
                  '${classItem.time}\nRoom: ${classItem.room}\nLecturer: ${classItem.instructor}'),
              isThreeLine: true,
            ),
          );
        },
      ),
    );
  }

  /// Navigation buttons to move between days
  Widget _buildNavigationButtons() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        IconButton(
          icon: Icon(Icons.arrow_back),
          onPressed: () {
            setState(() {
              _selectedDay = _selectedDay.subtract(Duration(days: 1));
            });
          },
        ),
        IconButton(
          icon: Icon(Icons.arrow_forward),
          onPressed: () {
            setState(() {
              _selectedDay = _selectedDay.add(Duration(days: 1));
            });
          },
        ),
      ],
    );
  }
}
