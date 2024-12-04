/// Event model class
class Event {
  final String eventName;
  final String eventStartTime;
  final String eventEndTime;
  final String eventDate;
  final String eventLocation;

  Event({
    required this.eventName,
    required this.eventStartTime,
    required this.eventEndTime,
    required this.eventDate,
    required this.eventLocation,
  });

  factory Event.fromJson(Map<String, dynamic> json) {
    return Event(
      eventName: json['event_name'],
      eventStartTime: json['event_start_time'],
      eventEndTime: json['event_end_time'],
      eventDate: json['event_date'],
      eventLocation: json['event_location'],
    );
  }
}
