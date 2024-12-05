class Event {
  final int id;
  final int moduleId;
  final String eventName;
  final String eventStartTime;
  final String eventEndTime;
  final String eventDate;
  final String eventLocation;

  Event({
    required this.id,
    required this.moduleId,
    required this.eventName,
    required this.eventStartTime,
    required this.eventEndTime,
    required this.eventDate,
    required this.eventLocation,
  });

  factory Event.fromJson(Map<String, dynamic> json) {
    return Event(
      id: json['id'],
      moduleId: json['moduleId'],
      eventName: json['name'],
      eventStartTime: json['startTime'],
      eventEndTime: json['endTime'],
      eventDate: json['date'],
      eventLocation: json['room'],
    );
  }
}
