/*
 *  Title: group_chat_info.dart
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */

class GroupChatInfo {
  final String chatname;
  final String name;
  final String time;
  final String moduleName;
  final String moduleTime;
  final String moduleCode;
  final List<String> studentsList;
  final List<String> teachersList;

  GroupChatInfo({
    required this.chatname,
    required this.name,
    required this.time,
    required this.moduleName,
    required this.moduleTime,
    required this.moduleCode,
    required this.studentsList,
    required this.teachersList,
  });
}
