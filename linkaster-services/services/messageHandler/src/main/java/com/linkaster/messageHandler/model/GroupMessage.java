package com.linkaster.messageHandler.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "public_messages")
public class GroupMessage {
    
    @Id
    @Column(name = "group_message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupMessageId;

    @Column(name = "group_id")
    private long groupId;

    @Column(name = "sender_id")
    private long senderId;

    @Column(name = "group_chat_id")
    private long groupChatId;

    @Column(name = "encrypted_message")
    private String encryptedMessage;

    @Column(name = "timestamp")
    private Date timestamp;

    
}
