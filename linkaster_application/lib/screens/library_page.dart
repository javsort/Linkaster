import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class LibraryPage extends StatefulWidget {
  @override
  _LibraryPageState createState() => _LibraryPageState();
}

class _LibraryPageState extends State<LibraryPage> {
  final List<String> timeSlots = [];
  String? selectedCheckInTime;
  String? selectedCheckoutTime;
  String? selectedSpace;
  DateTime? selectedDate;

  final List<String> availableDesks =
      List.generate(25, (index) => 'Desk ${index + 1}');
  final List<String> availableSeminars =
      List.generate(4, (index) => 'Seminar Room ${index + 1}');

  @override
  void initState() {
    super.initState();
    for (int hour = 6; hour <= 20; hour++) {
      for (int minute = 0; minute < 60; minute += 30) {
        String time = hour.toString().padLeft(2, '0') +
            ':' +
            minute.toString().padLeft(2, '0');
        timeSlots.add(time);
      }
    }
  }

  List<String> getFilteredCheckoutTimes() {
    if (selectedCheckInTime == null) return timeSlots;
    int checkInIndex = timeSlots.indexOf(selectedCheckInTime!);
    return timeSlots
        .sublist(checkInIndex + 1); // Only times after the check-in time
  }

  void _pickDate() async {
    DateTime? picked = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime.now(),
      lastDate: DateTime.now().add(Duration(days: 30)), // Next 30 days only
    );
    if (picked != null) {
      setState(() {
        selectedDate = picked;
      });
    }
  }

  String _getFormattedDate() {
    if (selectedDate == null) return 'Select Date';
    return DateFormat('EEEE, MMMM d, yyyy').format(selectedDate!);
  }

  void _bookSpace() {
    if (selectedDate != null &&
        selectedCheckInTime != null &&
        selectedCheckoutTime != null &&
        selectedSpace != null) {
      String formattedDate = DateFormat('yyyy-MM-dd').format(selectedDate!);
      showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('Booking Confirmation'),
            content: Text(
                '$selectedSpace has been booked on $formattedDate from $selectedCheckInTime to $selectedCheckoutTime.'),
            actions: [
              TextButton(
                onPressed: () => Navigator.of(context).pop(),
                child: Text('OK'),
              ),
            ],
          );
        },
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Please fill all fields to book a space.')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Library')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Row for Date and Time selections
            Row(
              children: [
                // Date Selection
                Expanded(
                  child: ElevatedButton(
                    onPressed: _pickDate,
                    child: Text(
                      _getFormattedDate(),
                      style: TextStyle(fontSize: 16),
                      overflow: TextOverflow.ellipsis,
                    ),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Theme.of(context).primaryColor,
                      foregroundColor: Colors.white,
                      padding:
                          EdgeInsets.symmetric(horizontal: 16, vertical: 12),
                    ),
                  ),
                ),
                SizedBox(width: 16),
                // Check-in Time Selection
                Expanded(
                  child: DropdownButton<String>(
                    isExpanded: true,
                    hint: Text('Check-in Time'),
                    value: selectedCheckInTime,
                    onChanged: (String? newValue) {
                      setState(() {
                        selectedCheckInTime = newValue;
                        selectedCheckoutTime = null;
                      });
                    },
                    items:
                        timeSlots.map<DropdownMenuItem<String>>((String time) {
                      return DropdownMenuItem<String>(
                        value: time,
                        child: Text(time),
                      );
                    }).toList(),
                  ),
                ),
                SizedBox(width: 16),
                // Check-out Time Selection
                Expanded(
                  child: DropdownButton<String>(
                    isExpanded: true,
                    hint: Text('Check-out Time'),
                    value: selectedCheckoutTime,
                    onChanged: (String? newValue) {
                      setState(() {
                        selectedCheckoutTime = newValue;
                      });
                    },
                    items: getFilteredCheckoutTimes()
                        .map<DropdownMenuItem<String>>((String time) {
                      return DropdownMenuItem<String>(
                        value: time,
                        child: Text(time),
                      );
                    }).toList(),
                  ),
                ),
              ],
            ),
            SizedBox(height: 20),
            Text('Available Spaces:', style: TextStyle(fontSize: 18)),
            SizedBox(height: 10),
            Expanded(
              child: GridView.builder(
                gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 5,
                  childAspectRatio: 1,
                  crossAxisSpacing: 8.0,
                  mainAxisSpacing: 8.0,
                ),
                itemCount: availableDesks.length + availableSeminars.length,
                itemBuilder: (context, index) {
                  if (index < availableSeminars.length) {
                    return GestureDetector(
                      onTap: () {
                        setState(() {
                          selectedSpace = availableSeminars[index];
                        });
                      },
                      child: Container(
                        decoration: BoxDecoration(
                          color: selectedSpace == availableSeminars[index]
                              ? Colors.green
                              : Colors.blueAccent,
                          borderRadius: BorderRadius.circular(8.0),
                        ),
                        alignment: Alignment.center,
                        child: Text(
                          availableSeminars[index],
                          style: TextStyle(
                              color: Colors.white, fontWeight: FontWeight.bold),
                        ),
                      ),
                    );
                  } else {
                    int deskIndex = index - availableSeminars.length;
                    return GestureDetector(
                      onTap: () {
                        setState(() {
                          selectedSpace = availableDesks[deskIndex];
                        });
                      },
                      child: Container(
                        decoration: BoxDecoration(
                          color: selectedSpace == availableDesks[deskIndex]
                              ? Colors.green
                              : Colors.orange,
                          borderRadius: BorderRadius.circular(8.0),
                        ),
                        alignment: Alignment.center,
                        child: Text(
                          availableDesks[deskIndex],
                          style: TextStyle(
                              color: Colors.white, fontWeight: FontWeight.bold),
                        ),
                      ),
                    );
                  }
                },
              ),
            ),
            ElevatedButton(
              onPressed: _bookSpace,
              child: Text('Submit'),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.red,
                foregroundColor: Colors.white,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
