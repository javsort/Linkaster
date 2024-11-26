package com.linkaster.messageHandler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.linkaster.messageHandler.dto.ActorMetadata;
import com.linkaster.messageHandler.dto.PrivateChatReg;
import com.linkaster.messageHandler.message.p2p.PrivateChat;
import com.linkaster.messageHandler.model.PrivateMessage;
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


    @Autowired
    private PrivateMessageRepository privateMessageRepository;

    
    @Autowired
    private PrivateChatRepository privateChatRepository;

    @Autowired
    private MessageKeyMaster keyMaster;

    private final String log_header = "PrivateMessagingManagerService --- ";

    // Constructor
    public PrivateMessagingManagerService(PrivateMessageRepository privateMessageRepository, PrivateChatRepository privateChatRepository, MessageKeyMaster keyMaster) {
        this.privateMessageRepository = privateMessageRepository;
        this.privateChatRepository = privateChatRepository;
        this.keyMaster = keyMaster;
    }

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

    public boolean sendMessage(long privateChatId, String message, long senderId){

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

        String encryptedMessage = null;
        // Call KeyMaster to encrypt the message with destinatary's public key
        try {
            encryptedMessage = keyMaster.encryptMessage(message, encPublic);

        } catch (Exception e){
            log.error(log_header + "Error during message encryption: " + e.getMessage());
            return false;
        }

        // Create a new message
        PrivateMessage newMessage = new PrivateMessage();
        newMessage.setPrivateChat(privateChat);
        newMessage.setSenderId(senderId);
        newMessage.setReceiverId(destinataryId);
        newMessage.setEncryptedMessage(encryptedMessage);
        newMessage.setTimestamp(new java.sql.Date(System.currentTimeMillis()));

        // Save the new message
        privateMessageRepository.save(newMessage);

        log.info(log_header + "Sending a message to private chat with id: '" + privateChatId + "'");

        return false;
    }

    public Iterable<PrivateMessage> getMessagesFromUser(long user1, long user2){
        log.info(log_header + "Retrieving messages between userId: '" + user1 + "'' and userId:'" + user2 + "'");

        // Get the private chat
        PrivateChat privateChat = privateChatRepository.findByUser1AndUser2(user1, user2);

        long privateChatId = privateChat.getPrivateChatId();

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
