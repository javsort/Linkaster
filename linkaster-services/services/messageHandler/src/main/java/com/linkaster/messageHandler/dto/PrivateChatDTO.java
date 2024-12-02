package com.linkaster.messageHandler.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PrivateChatDTO {
    private long privateChatId;
    private long senderId;  // User ID of the person who requested the chat
    private long destinataryId;  // User ID of the person who received the chat request
    private String receiverName;  // Name of the destinatary
    private Date lastMessageDate;  // Last message sent in the chat

}