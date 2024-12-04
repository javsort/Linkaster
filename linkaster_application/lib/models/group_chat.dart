class GroupChat {
  final int groupChatId;
  final int moduleId;
  final String moduleName;
  final String lastMessageDate;

  GroupChat({
    required this.groupChatId,
    required this.moduleId,
    required this.moduleName,
    required this.lastMessageDate,
  });

  factory GroupChat.fromJson(Map<String, dynamic> json) {
    return GroupChat(
      groupChatId: json['groupChatId'],
      moduleId: json['moduleId'],
      moduleName: json['moduleName'],
      lastMessageDate: json['lastMessageDate'],
    );
  }
}
