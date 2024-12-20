package com.linkaster.messageHandler.model.p2p;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
 *  Title: PrivateMessage.java
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
@Table(name = "private_messages")
public class PrivateMessage {
    
    @Id
    @Column(name = "private_message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long privateMessageId;

    @ManyToOne
    @JoinColumn(name = "private_chat_id", nullable = false)
    @JsonBackReference
    private PrivateChat privateChat;

    @Column(name = "sender_id")
    private long senderId;

    @Column(name = "receiver_id")
    private long receiverId;

    @Column(name = "encrypted_message")
    private String encryptedMessage;

    @Column(name = "timestamp")
    private Date timestamp;

}