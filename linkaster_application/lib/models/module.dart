class Module {
  final String subject;
  final String time;
  final String room;
  final String instructor;
  final bool isMandatory; // New property to indicate if the class is mandatory

  Module({
    required this.subject,
    required this.time,
    required this.room,
    required this.instructor,
    this.isMandatory = false, // Default to false if not specified
  });
}
