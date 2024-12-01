class Chat {
  final int privateChatId;
  final int senderId;
  final int destinataryId;
  final String receiverName;
  final String lastMessageDate;

  Chat({
    required this.privateChatId,
    required this.senderId,
    required this.destinataryId,
    required this.receiverName,
    required this.lastMessageDate,
  });

  factory Chat.fromJson(Map<String, dynamic> json) {
    return Chat(
      privateChatId: json['privateChatId'],
      senderId: json['senderId'],
      destinataryId: json['destinataryId'],
      receiverName: json['receiverName'],
      lastMessageDate: json['lastMessageDate'],
    );
  }
}
