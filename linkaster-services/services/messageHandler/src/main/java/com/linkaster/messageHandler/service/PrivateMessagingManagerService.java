package com.linkaster.messageHandler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.linkaster.messageHandler.dto.PrivateMessageDTO;
import com.linkaster.messageHandler.model.ActorMetadata;
import com.linkaster.messageHandler.model.p2p.PrivateChat;
import com.linkaster.messageHandler.model.p2p.PrivateChatReg;
import com.linkaster.messageHandler.model.p2p.PrivateMessage;
import com.linkaster.messageHandler.repository.PrivateChatRepository;
import com.linkaster.messageHandler.repository.PrivateMessageRepository;
import com.linkaster.messageHandler.util.MessageKeyMaster;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/*
 * Will handle all private messaging related services
 */

@Service
@Transactional
@Slf4j
public class PrivateMessagingManagerService {

    private final PrivateMessageRepository privateMessageRepository;

    private final PrivateChatRepository privateChatRepository;

    private final MessageKeyMaster keyMaster;

    private final String log_header = "PrivateMessagingManagerService --- ";

    // Constructor
    @Autowired
    public PrivateMessagingManagerService(PrivateMessageRepository privateMessageRepository, PrivateChatRepository privateChatRepository, MessageKeyMaster keyMaster) {
        this.privateMessageRepository = privateMessageRepository;
        this.privateChatRepository = privateChatRepository;
        this.keyMaster = keyMaster;
    }

    // Create a new private chat
    public boolean createPrivateChat(PrivateChatReg newChat){
        log.info(log_header + "Creating a new private chat between userId: '" + newChat.getUser1().getUserId() + "'' and userId:'" + newChat.getUser2().getUserId() + "'");

        // Create a new private chat
        PrivateChat newPrivateChat = new PrivateChat();
        newPrivateChat.setUser1(newChat.getUser1());
        newPrivateChat.setUser2(newChat.getUser2());

        // Save the new chat
        privateChatRepository.save(newPrivateChat);

        return true;
    }

    // Authenticate access to a private chat
    public boolean authenticateChatAccess(long userId, long privateChatId){
        log.info(log_header + "Authenticating access to private chat with id: '" + privateChatId + "' for user with id: '" + userId + "'");

        // Get the private chat
        PrivateChat privateChat = privateChatRepository.findById(privateChatId).orElse(null);

        if(privateChat == null){
            log.error(log_header + "Error: Private chat with id: '" + privateChatId + "' not found");
            return false;
        }

        // Check if the user is part of the chat
        if(privateChat.getUser1().getUserId() == userId || privateChat.getUser2().getUserId() == userId){
            log.info(log_header + "User with id: '" + userId + "' has access to private chat with id: '" + privateChatId + "'");
            return true;
        }

        log.error(log_header + "Error: User with id: '" + userId + "' does not have access to private chat with id: '" + privateChatId + "'");
        return false;

    }
    
    // Send a message to a private chat
    public boolean sendMessage(PrivateMessageDTO messageObj, long senderId){
        // Unwrap the message object
        long privateChatId = messageObj.getPrivateChatId();
        String message = messageObj.getMessage();
        
        log.info(log_header + "Handling private message for chat Id: " + privateChatId);

        // Get the private chat
        PrivateChat privateChat = privateChatRepository.findById(privateChatId).orElse(null);

        if(privateChat == null){
            log.error(log_header + "Error: Private chat with id: '" + privateChatId + "' not found");
            return false;
        }

        // Get destinatary's public key
        ActorMetadata destinataryInfo = privateChat.getDestData(senderId);
        String encPublic = destinataryInfo.getPublicKey();
        long destinataryId = destinataryInfo.getUserId();
        
        if(encPublic == null){
            log.error(log_header + "Error: Sender's public key not found");
            return false;
        }

        // Call KeyMaster to encrypt the message with destinatary's public key
        try {
            log.info(log_header + "Encrypting message with destinatary's public key...");
            //String encryptedMessage = keyMaster.encryptMessage(message, encPublic);
            
            // Create a new message
            PrivateMessage newMessage = new PrivateMessage();
            newMessage.setPrivateChat(privateChat);
            newMessage.setSenderId(senderId);
            newMessage.setReceiverId(destinataryId);
            newMessage.setEncryptedMessage(message);            // FOR TESTING PURPOSES
            newMessage.setTimestamp(new java.sql.Date(System.currentTimeMillis()));

            // Save the new message
            privateMessageRepository.save(newMessage);

            log.info(log_header + "Sending a message to private chat with id: '" + privateChatId + "'...");

            return true;

        } catch (Exception e){
            log.error(log_header + "Error during message encryption: " + e.getMessage() + " - " + e.getCause());
            return false;
        }
    }
   
    // Retrieve messages from a private chat
    public Iterable<PrivateMessage> getPrivateChat(long privateChatId){

        log.info(log_header + "Retrieving messages from private chat with id: '" + privateChatId + "'");


        // Get the private chat
        PrivateChat privateChat = privateChatRepository.findById(privateChatId).orElse(null);

        if(privateChat == null){
            log.error(log_header + "Error: Private chat with id: '" + privateChatId + "' not found");
            return null;
        }

        // Retrieve a reasonable amount of messages
        
        // Get the messages
        Iterable<PrivateMessage> messages = privateMessageRepository.findByPrivateChatId(privateChatId);

        return messages;
    }

    // Scheduled task to clean all private chats - runs at midnight on the first of every month
    @Scheduled(cron = "0 0 0 1 * ?")
    public void cleanChats(){
        log.info(log_header + "Cleaning all private chats");

        privateChatRepository.deleteAll();
    }

    
}
