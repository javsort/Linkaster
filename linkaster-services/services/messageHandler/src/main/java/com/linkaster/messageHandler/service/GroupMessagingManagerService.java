
package com.linkaster.messageHandler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.messageHandler.model.UserInfo;
import com.linkaster.messageHandler.model.group.GroupChat;
import com.linkaster.messageHandler.model.group.GroupChatReg;
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
        byte[] encryptedModuleAESKey = keyMaster.encryptAESKeyWithAppPublicKey();


        // Create a new group chat
        GroupChat newGroupChat = new GroupChat();
        newGroupChat.setEncryptedModuleAESKey(encryptedModuleAESKey);
        newGroupChat.setModuleName(moduleName);
        newGroupChat.setModuleId(moduleId);
        newGroupChat.setOwnerUserId(ownerUserId);


        log.info(log_header + "Creating new chat for module: ");
        return true;
    }

    public boolean addUserToGroupChat(UserInfo newUser, long moduleChatId){
        // Get relevant data from DTO
        long userId = newUser.getUserId();

        // Verify & retrieve the groupChat for that module
        GroupChat groupChatToAdd = groupChatRepository.findById(moduleChatId).orElse(null);
        if(groupChatToAdd == null){
            log.error(log_header + "Unable to find groupChat with Id: '" + moduleChatId + "'");
        }

        // Add the user to the groupChat
        groupChatToAdd.addMember(newUser.getUserId(), newUser.getPublicKey());

        log.info(log_header + "User with id: " + userId + " was added successfully to the '" + groupChatToAdd.getModuleName() + "' module.");
        groupChatRepository.save(groupChatToAdd);



        long userToJoinId = newUser.getUserId();
        String userToJoinPubKey = newUser.getPublicKey();

        return true;
    }
}