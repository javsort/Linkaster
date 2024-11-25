package com.linkaster.messageHandler.message.p2p;
// p2p -> private to private chat

import com.linkaster.messageHandler.model.UserInfo;

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

/*
 * This is the PrivateChat entity class. It represents a private chat in the system.
 * It uses RSA encryption for the messages & the RSA key is encrypted with each user's public Key.
 * 
 * To be implemented in the future.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "private_chats")
public class PrivateChat {

    @Id
    @Column(name = "group_chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long privateChatId;

    /*
     * UserInfo includes:
     * - User ID
     * - User's public key
     * - User's name 
     * - User's email
     * - User's role
     */

    @Column(name = "user1")
    private UserInfo user1;

    @Column(name = "user2")
    private UserInfo user2;

    /*
     * STATUS will be pinged from user-handler
     * 
     * -> TO be an unverified GET, it's very simple, keep low overhead
     */
    
}
