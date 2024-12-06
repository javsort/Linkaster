package com.linkaster.messageHandler.model.group;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 *  Title: GroupMessage.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "group_messages")
public class GroupMessage {

    @Id
    @Column(name = "group_message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupMessageId;

    @Column(name = "sender_id", nullable = false)
    private long senderId;

    @Column(name = "encrypted_message", nullable = false)
    private String encryptedMessage;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "group_chat_id", nullable = false)
    private GroupChat groupChat;
}
