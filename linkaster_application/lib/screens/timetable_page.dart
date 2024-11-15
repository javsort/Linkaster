import 'package:flutter/material.dart';
import 'package:table_calendar/table_calendar.dart';
import '../models/module.dart';

class TimetablePage extends StatefulWidget {
  @override
  _TimetablePageState createState() => _TimetablePageState();
}

class _TimetablePageState extends State<TimetablePage> {
  DateTime _selectedDay = DateTime.now(); // Currently selected day
  CalendarFormat _calendarFormat =
      CalendarFormat.week; // Initial calendar format

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
    DateTime(2024, 10, 31): [
      Module(
        subject: "Software Design Studio Project II",
        time: "11:00 AM - 14:00 PM",
        room: "650",
        instructor: "Dr. Aakash Ahmad",
        isMandatory: true, // Mandatory class
      ),
      Module(
        subject: "Language and Compilation",
        time: "16:00 PM - 18:00 PM",
        room: "715",
        instructor: "Dr. Marco Caminati",
        isMandatory: false, // Non-mandatory class
      ),
      Module(
        subject: "German Language B1",
        time: "18:00 PM - 20:00 PM",
        room: "706",
        instructor: "Prof. Barbara Osnowski",
        isMandatory: true, // Mandatory class
      ),
    ],
    DateTime(2024, 11, 1): [
      Module(
        subject: "Internet Applications Engineering",
        time: "10:00 AM - 11:00 AM",
        room: "625",
        instructor: "Dr. David Georg Reichelt",
        isMandatory: false, // Non-mandatory class
      ),
      Module(
        subject: "Language and Compilation",
        time: "15:00 PM - 17:00 PM",
        room: "635",
        instructor: "Dr. Marco Caminati",
        isMandatory: true, // Mandatory class
      ),
    ],
  };

  DateTime normalizeDate(DateTime date) {
    return DateTime(date.year, date.month, date.day); // Normalize to midnight
  }

  @override
  Widget build(BuildContext context) {
    return Column(
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
    );
  }

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
              title: Text(classItem.subject,
                  style: TextStyle(fontWeight: FontWeight.bold)),
              subtitle: Text(
                  '${classItem.time}\nRoom: ${classItem.room} \nLecturer: ${classItem.instructor}'),
              isThreeLine: true,
            ),
          );
        },
      ),
    );
  }

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
