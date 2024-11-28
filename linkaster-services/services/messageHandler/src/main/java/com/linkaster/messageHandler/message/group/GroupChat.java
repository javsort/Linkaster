package com.linkaster.messageHandler.message.group;
// group -> group chat

import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/*
 * This is the GroupChat entity class. It represents a group chat in the system.
 * It uses AES encryption for the messages & the AES key is encrypted with each member's public Key.
 * -> Hybrid encryption
 * 
 * 
 * To be implemented in the future.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "group_chats")
public class GroupChat {

    
    @Id
    @Column(name = "group_chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupChatId;

    @Column(name = "AESKey")
    private String AESKey;

    @Column(name = "moduleId")
    private long moduleId;

    @Column(name = "moduleName")
    private String moduleName;

    @Column(name = "ownerUserId")
    private long ownerUserId;

    // Map of group members - key is the user id, value is the user's public key
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "group_chat_members", joinColumns = @JoinColumn(name = "group_chat_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "public_key")
    private Map<Long, String> groupMembers;

}
