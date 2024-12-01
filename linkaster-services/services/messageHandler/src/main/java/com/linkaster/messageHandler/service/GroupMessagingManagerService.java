
package com.linkaster.messageHandler.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.linkaster.messageHandler.dto.GroupChatDTO;
import com.linkaster.messageHandler.dto.GroupMessageDTO;
import com.linkaster.messageHandler.dto.GroupMessageReturnDTO;
import com.linkaster.messageHandler.model.UserInfo;
import com.linkaster.messageHandler.model.group.GroupChat;
import com.linkaster.messageHandler.model.group.GroupChatReg;
import com.linkaster.messageHandler.model.group.GroupMessage;
import com.linkaster.messageHandler.repository.GroupChatRepository;
import com.linkaster.messageHandler.repository.GroupMessageRepository;
import com.linkaster.messageHandler.util.MessageKeyMaster;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class GroupMessagingManagerService {

    private GroupMessageRepository groupMessageRepository;
    private GroupChatRepository groupChatRepository;
    private MessageKeyMaster keyMaster;

    private final String log_header = "GroupMessagingManagerService --- ";

    @Autowired
    public GroupMessagingManagerService(GroupMessageRepository groupMessageRepository, GroupChatRepository groupChatRepository, MessageKeyMaster keyMaster){
        this.groupMessageRepository = groupMessageRepository;
        this.groupChatRepository = groupChatRepository;
        this.keyMaster = keyMaster;
    }

    // THIS IS PINGED BY MODULE MANAGER EVERY TIME A NEW MODULE IS CREATED
    public boolean createChatForModule(GroupChatReg newChat) throws Exception {

        long moduleId = newChat.getModuleId();
        String moduleName = newChat.getModuleName();
        long ownerUserId = newChat.getOwnerUserId();

        // Generate & Encrypt the Module's AES Key with the application's public key
        String encryptedModuleAESKey = keyMaster.encryptAESKeyWithAppPublicKey();


        // Create a new group chat
        GroupChat newGroupChat = new GroupChat();
        newGroupChat.setModuleAESKey(encryptedModuleAESKey);
        newGroupChat.setModuleName(moduleName);
        newGroupChat.setModuleId(moduleId);
        newGroupChat.setOwnerUserId(ownerUserId);


        log.info(log_header + "Creating new chat for module: ");
        return true;
    }


    /*
     * Authenticate access to a group chat
     */
    public boolean authenticateChatAccess(long userId, long moduleChatId) {
        // Retrieve the groupChat
        GroupChat groupChat = groupChatRepository.findById(moduleChatId).orElse(null);
        if(groupChat == null){
            log.error(log_header + "Unable to find groupChat with Id: '" + moduleChatId + "'");
            return false;
        }

        // Check if the user is a member of the groupChat
        if(groupChat.getGroupMembers().containsKey(userId)){
            log.info(log_header + "User with id: " + userId + " is a member of the '" + groupChat.getModuleName() + "' module.");
            return true;
        } else {
            log.error(log_header + "User with id: " + userId + " is not a member of the '" + groupChat.getModuleName() + "' module.");
            return false;
        }
    }

    public GroupMessageReturnDTO sendMessage(GroupMessageDTO incGroupMessage) {
        // Get relevant data from DTO
        long moduleChatId = incGroupMessage.getGroupChatId();
        long senderId = incGroupMessage.getSenderId();
        String message = incGroupMessage.getMessage();
        
        // Retrieve the groupChat
        GroupChat groupChat = groupChatRepository.findById(moduleChatId).orElse(null);
        if(groupChat == null){
            log.error(log_header + "Unable to find groupChat with Id: '" + moduleChatId + "'");
            return null;
        }

        // Check if the sender is a member of the groupChat
        if(!groupChat.getGroupMembers().containsKey(senderId)){
            log.error(log_header + "User with id: " + senderId + " is not a member of the '" + groupChat.getModuleName() + "' module.");
            return null;
        }

        // Get sender name
        String senderName = groupChat.getGroupMembers().get(senderId);

        // Encrypt the message with the module's AES key
        try {
            String encryptedMessage = keyMaster.encryptMessage(message, groupChat.getModuleAESKey());   // Should be user's version of it but aight
    
            // Create a new group message
            GroupMessage newGroupMessage = new GroupMessage();
            newGroupMessage.setModuleId(groupChat.getModuleId());
            newGroupMessage.setSenderId(senderId);
            newGroupMessage.setGroupChat(groupChat);
            newGroupMessage.setEncryptedMessage(encryptedMessage);
            newGroupMessage.setTimestamp(new Date(System.currentTimeMillis()));
    
    
            // Save the new group message
            groupMessageRepository.save(newGroupMessage);
    
            // Return the DTO
            return new GroupMessageReturnDTO(message, moduleChatId, senderId, senderName);
            
        } catch (Exception e) {
            log.error(log_header + "Error encrypting message: " + e.getMessage());
            return null;
        }
    }

    /*
     * Add a user to a group chat
     */
    public boolean addUserToGroupChat(UserInfo newUser, long moduleChatId){
        // Get relevant data from DTO
        long userId = newUser.getUserId();
        String userPubKey = newUser.getPublicKey();

        // Verify & retrieve the groupChat for that module
        GroupChat groupChatToAdd = groupChatRepository.findById(moduleChatId).orElse(null);
        if(groupChatToAdd == null){
            log.error(log_header + "Unable to find groupChat with Id: '" + moduleChatId + "'");
        }

        // Add the user to the groupChat
        groupChatToAdd.addMember(userId, userPubKey);

        log.info(log_header + "User with id: " + userId + " was added successfully to the '" + groupChatToAdd.getModuleName() + "' module.");
        groupChatRepository.save(groupChatToAdd);

        return true;
    }

    /*
     * Access through endpoints with Controller
     */

    public ResponseEntity<Iterable<GroupChatDTO>> getGroupChats4User(long userId){
        // Make groupChat DTO's for requester user

        // Get the group chats
        Iterable<GroupChat> groupChats = groupChatRepository.getGroupChats4User(userId);

        List<GroupChatDTO> usersGroupChats = new ArrayList<>();
        for(GroupChat chat : groupChats){
            GroupChatDTO chatDTO = new GroupChatDTO(chat.getGroupChatId(), chat.getModuleId(), chat.getModuleName(), chat.getGroupMembers(), chat.getLastMessageDate());
            usersGroupChats.add(chatDTO);
        }

        return ResponseEntity.ok(usersGroupChats);
    }

    public ResponseEntity<?> getUserGroupChat(long moduleChatId){
        // Get the group chat
        log.info(log_header + "Retrieving group chat with id: '" + moduleChatId + "'");

        GroupChat requestedGroupChat = groupChatRepository.findById(moduleChatId).orElse(null);

        if(requestedGroupChat == null){
            log.error(log_header + "Error: Group chat with id: '" + moduleChatId + "' not found");
            return null;
        }

        // Retrieve the groupChat's messages
        Iterable<GroupMessage> messages = groupMessageRepository.findByGroupChatId(moduleChatId);

        // Create a DTO for the groupChat
        List<GroupMessageReturnDTO> groupsMessages = new ArrayList<>();

        for(GroupMessage message : messages){
            GroupMessageReturnDTO messageDTO = new GroupMessageReturnDTO(message.getEncryptedMessage(), message.getGroupChat().getGroupChatId(), message.getSenderId(), requestedGroupChat.getGroupMembers().get(message.getSenderId()));
            groupsMessages.add(messageDTO);
        }

        return ResponseEntity.ok(groupsMessages);
    }
}