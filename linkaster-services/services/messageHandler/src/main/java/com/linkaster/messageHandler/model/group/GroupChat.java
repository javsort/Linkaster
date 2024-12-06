package com.linkaster.messageHandler.model.group;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 *  Title: GroupChat.java
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
@Table(name = "group_chats")
public class GroupChat {

    @Id
    @Column(name = "group_chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupChatId;

    @Column(name = "last_message_date")
    private Date lastMessageDate;

    @Column(name = "module_AESKey", nullable = false, length = 512)
    private String moduleAESKey;

    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "owner_user_id")
    private Long ownerUserId;

    // Map of group members - key is the user id, value is the user's public key
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "group_chat_members", joinColumns = @JoinColumn(name = "group_chat_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "public_key")
    private Map<Long, String> groupMembers;

    // Relationship to GroupMessage
    @OneToMany(mappedBy = "groupChat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMessage> groupMessages;

    // Utility methods
    public void addMember(long userId, String publicKey) {
        groupMembers.put(userId, publicKey);
    }
}
