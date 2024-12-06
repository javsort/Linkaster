package com.linkaster.messageHandler.model.p2p;
// p2p -> private to private chat

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.linkaster.messageHandler.model.ActorMetadata;
import com.linkaster.messageHandler.model.UserInfo;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
/*
 *  Title: PrivateChat.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "private_chats")
public class PrivateChat {

    @Id
    @Column(name = "private_chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long privateChatId;

    @OneToMany(mappedBy = "privateChat")
    @JsonManagedReference
    private List<PrivateMessage> privateMessages;

    @Column(name = "last_message_date")
    private Date lastMessageDate;

    /*
     * UserInfo includes:
     * - User ID
     * - User's public key
     * - User's name 
     * - User's email
     * - User's role
     */

    @Embedded
    @AttributeOverrides({
    @AttributeOverride(name = "userId", column = @Column(name = "user1_id")),
    @AttributeOverride(name = "publicKey", column = @Column(name = "user1_public_key", columnDefinition = "LONGTEXT")),
    @AttributeOverride(name = "name", column = @Column(name = "user1_name")),
    @AttributeOverride(name = "email", column = @Column(name = "user1_email")),
    @AttributeOverride(name = "role", column = @Column(name = "user1_role"))
    })
    private UserInfo user1;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "userId", column = @Column(name = "user2_id")),
        @AttributeOverride(name = "publicKey", column = @Column(name = "user2_public_key", columnDefinition = "LONGTEXT")),
        @AttributeOverride(name = "name", column = @Column(name = "user2_name")),
        @AttributeOverride(name = "email", column = @Column(name = "user2_email")),
        @AttributeOverride(name = "role", column = @Column(name = "user2_role"))
    })
    private UserInfo user2;

    /*
     * STATUS will be pinged from user-handler
     * 
     * -> TO be an unverified GET, it's very simple, keep low overhead
     */

    //Extra setters bc lombok hates life
    public void setUser1(UserInfo user1) {
        this.user1 = user1;
    }

    public void setUser2(UserInfo user2) {
        this.user2 = user2;
    }

    // Retrieve the key that is NOT the method caller's key
    public ActorMetadata getDestData(long userId){
        String keyToReturn = null;
        long userIdToReturn = 0;
        String nameToReturn = null;

        if(user1.getUserId() == userId){
            keyToReturn = user2.getPublicKey();
            userIdToReturn = user2.getUserId();
            nameToReturn = user2.getName();

        } else {
            keyToReturn = user1.getPublicKey();
            userIdToReturn = user1.getUserId();
            nameToReturn = user1.getName();
        }

        ActorMetadata actorMetadata = new ActorMetadata(keyToReturn, userIdToReturn, nameToReturn);

        return actorMetadata;
    }

    // Check if the user is part of the chat
    public boolean isUserInChat(long userId){
        return user1.getUserId() == userId || user2.getUserId() == userId;
    }
    
}
