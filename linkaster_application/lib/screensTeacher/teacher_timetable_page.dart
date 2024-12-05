import 'package:flutter/material.dart';
import 'package:table_calendar/table_calendar.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../models/event.dart'; // Ensure your Event model matches the data structure
import '../config/config.dart';
import 'package:shared_preferences/shared_preferences.dart';

class TimetablePage extends StatefulWidget {
  final String? token;

  const TimetablePage({Key? key, this.token}) : super(key: key);

  @override
  _TimetablePageState createState() => _TimetablePageState();
}

class _TimetablePageState extends State<TimetablePage> {
  DateTime _selectedDay = DateTime.now();
  CalendarFormat _calendarFormat = CalendarFormat.week;
  String? token;

  final Map<DateTime, List<Event>> _events = {};

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
    });

    if (token != null) {
      _fetchTimetableData(_selectedDay);
    }
  }

  /// Fetch events from API based on the selected date
  Future<void> _fetchTimetableData(DateTime selectedDate) async {
    final apiUrl = '${AppConfig.apiBaseUrl}/api/timetable/getEvents';

    try {
      final response = await http.get(
        Uri.parse(apiUrl),
        headers: {
          'Authorization': 'Bearer $token',
        },
      );

      if (response.statusCode == 200) {
        final Map<String, dynamic> responseData = json.decode(response.body);
        final List<dynamic> eventsData = responseData['upcomingEvents'];

        final List<Event> events =
            eventsData.map((event) => Event.fromJson(event)).toList();

        setState(() {
          _events.clear(); // Clear existing data
          for (var event in events) {
            final eventDate = DateTime.parse(
                event.eventDate); // Assuming 'eventDate' is the date field
            final normalizedDate = normalizeDate(eventDate);

            if (_events[normalizedDate] == null) {
              _events[normalizedDate] = [];
            }
            _events[normalizedDate]?.add(event);
          }
        });
      } else {
        print('Failed to load events. Status code: ${response.statusCode}');
      }
    } catch (error) {
      print('Error fetching events: $error');
    }
  }

  DateTime normalizeDate(DateTime date) {
    return DateTime(date.year, date.month, date.day);
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
              _fetchTimetableData(selectedDay);
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

  Widget _buildDailyClassList() {
    final normalizedDate = normalizeDate(_selectedDay);
    final classes = _events[normalizedDate] ?? [];

    if (classes.isEmpty) {
      return Center(
        child: Text(
          'No events scheduled for ${normalizedDate.toLocal().toString().split(' ')[0]}.',
        ),
      );
    }

    return Expanded(
      child: ListView.builder(
        itemCount: classes.length,
        itemBuilder: (context, index) {
          final event = classes[index];
          return Card(
            margin: EdgeInsets.symmetric(vertical: 4, horizontal: 16),
            child: ListTile(
              leading: Icon(Icons.event, color: Colors.blue),
              title: Text(
                event.eventName,
                style: TextStyle(fontWeight: FontWeight.bold),
              ),
              subtitle: Text(
                  '${event.eventStartTime} - ${event.eventEndTime}\nLocation: ${event.eventLocation}'),
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
            _fetchTimetableData(_selectedDay);
          },
        ),
        IconButton(
          icon: Icon(Icons.arrow_forward),
          onPressed: () {
            setState(() {
              _selectedDay = _selectedDay.add(Duration(days: 1));
            });
            _fetchTimetableData(_selectedDay);
          },
        ),
      ],
    );
  }
}
