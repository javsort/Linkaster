package com.linkaster.messageHandler.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.linkaster.messageHandler.dto.PrivateChatDTO;
import com.linkaster.messageHandler.dto.PrivateChatSeedDTO;
import com.linkaster.messageHandler.dto.PrivateMessageDTO;
import com.linkaster.messageHandler.model.ActorMetadata;
import com.linkaster.messageHandler.model.p2p.PrivateChat;
import com.linkaster.messageHandler.model.p2p.PrivateChatReg;
import com.linkaster.messageHandler.model.p2p.PrivateMessage;
import com.linkaster.messageHandler.repository.PrivateChatRepository;
import com.linkaster.messageHandler.repository.PrivateMessageRepository;
import com.linkaster.messageHandler.util.MessageKeyMaster;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/*
 * Will handle all private messaging related services
 */

@Service
@Transactional
@Slf4j
public class PrivateMessagingManagerService {

    @Value("${address.logicGateway.url}")
    private String logicGatewayAddress;

    private final PrivateMessageRepository privateMessageRepository;

    private final PrivateChatRepository privateChatRepository;

    private final RestTemplate restTemplate = new RestTemplate();

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
    public ResponseEntity<?> createPrivateChat(HttpServletRequest request, String destEmail) {
        String userId = request.getAttribute("id").toString();
        log.info(log_header + "Creating a new private chat for user requesting with id: '" + userId + "' to connect to user with email: '" + destEmail + "'");

        // Perform the request to get both users data
        String pathToRetrieveUserData = logicGatewayAddress + "/api/user/getDataForChat";

        // Create request back to gateway w token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        
        // Keep token on call for logicGateway & moduleManager re-verification of token 
        headers.set("Authorization", request.getHeader("Authorization"));
        
        // Add user info to headers
        // Add claims to the req attributes (opt, but would be [id, username, role])
        headers.set("id", userId);
        headers.set("userEmail", request.getAttribute("userEmail").toString());
        headers.set("role", request.getAttribute("role").toString());


        PrivateChatSeedDTO chatSeed = new PrivateChatSeedDTO();
        chatSeed.setDestEmail(destEmail);
        chatSeed.setRequesterId(userId);

        HttpEntity<PrivateChatSeedDTO> requestToUserHandler = new HttpEntity<>(chatSeed, headers);

        try {
            ResponseEntity<PrivateChatReg> response = restTemplate.exchange(
                pathToRetrieveUserData, 
                HttpMethod.POST, 
                requestToUserHandler, 
                PrivateChatReg.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                
                // Then create the chat form the received data
                PrivateChatReg newChat = response.getBody();

                // Create a new private chat
                PrivateChat newPrivateChat = new PrivateChat();
                newPrivateChat.setUser1(newChat.getUser1());
                newPrivateChat.setUser2(newChat.getUser2());

                // Save the new chat
                privateChatRepository.save(newPrivateChat);

                long userIdLong = Long.parseLong(userId);

                return getUsersPrivateChats(userIdLong);
            } else {
                log.error(log_header + "Error occurred while creating private chat for user: '" + userId + "'");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while calling user service to create private chat for user: '" + userId + "'");
            }

        } catch (Exception e) {
            log.error(log_header + "Error occurred while creating private chat for user: '" + userId + "'");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while creating private chat for user: '" + userId + "'");
        }
    }

    // Authenticate access to a private chat
    public long authenticateChatAccess(long userId, long privateChatId){
        log.info(log_header + "Authenticating access to private chat with id: '" + privateChatId + "' for user with id: '" + userId + "'");

        // Get the private chat
        PrivateChat privateChat = privateChatRepository.findById(privateChatId).orElse(null);

        if(privateChat == null){
            log.error(log_header + "Error: Private chat with id: '" + privateChatId + "' not found");
            return -1;
        }

        // Check if the user is part of the chat -> return the other user's id
        if(privateChat.getUser1().getUserId() == userId) {
            log.info(log_header + "User with id: '" + userId + "' has access to private chat with id: '" + privateChatId + "'");
            return privateChat.getUser2().getUserId();
        
        } else if (privateChat.getUser2().getUserId() == userId){
            log.info(log_header + "User with id: '" + userId + "' has access to private chat with id: '" + privateChatId + "'");
            return privateChat.getUser1().getUserId();
        }

        log.error(log_header + "Error: User with id: '" + userId + "' does not have access to private chat with id: '" + privateChatId + "'");
        return -1;

    }
    
    // Send a message to a private chat
    public PrivateMessage sendMessage(PrivateMessageDTO messageObj, long senderId){
        // Unwrap the message object
        long privateChatId = messageObj.getPrivateChatId();
        String message = messageObj.getMessage();
        
        log.info(log_header + "Handling private message for chat Id: " + privateChatId);

        // Get the private chat & update the last message date
        PrivateChat privateChat = privateChatRepository.findById(privateChatId).orElse(null);

        if(privateChat == null){
            log.error(log_header + "Error: Private chat with id: '" + privateChatId + "' not found");
            return null;
        }

        // Get destinatary's public key
        ActorMetadata destinataryInfo = privateChat.getDestData(senderId);
        String encPublic = destinataryInfo.getPublicKey();
        long destinataryId = destinataryInfo.getUserId();
        
        if(encPublic == null){
            log.error(log_header + "Error: Sender's public key not found");
            return null;
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

            // Update the last message date
            privateChat.setLastMessageDate(newMessage.getTimestamp());

            log.info(log_header + "Sending a message to private chat with id: '" + privateChatId + "'...");

            return newMessage;

        } catch (Exception e){
            log.error(log_header + "Error during message encryption: " + e.getMessage() + " - " + e.getCause());
            return null;
        }
    }


    /*public String getDecoded(long privateChatId, String encodedMessage, long userId){
        // Get the private chat
        PrivateChat privateChat = privateChatRepository.findById(privateChatId).orElse(null);

        if(privateChat == null){
            log.error(log_header + "Error: Private chat with id: '" + privateChatId + "' not found");
            return null;
        }

        // Get the destinatary's private key
        ActorMetadata destinataryInfo = privateChat.getDestData(userId);
        //String encPrivate = destinataryInfo.getPrivateKey();

        if(encPrivate == null){
            log.error(log_header + "Error: Destinatary's private key not found");
            return null;
        }

        // Call KeyMaster to decrypt the message with destinatary's private key
        try {
            log.info(log_header + "Decrypting message with destinatary's private key...");
            String decryptedMessage = keyMaster.decryptMessage(encodedMessage, encPrivate);

            return decryptedMessage;

        } catch (Exception e){
            log.error(log_header + "Error during message decryption: " + e.getMessage() + " - " + e.getCause());
            return null;
        }
    }*/

    public ResponseEntity<?> getUsersPrivateChats(long userId){
        log.info(log_header + "Retrieving private chats sample DTO's for user with id: '" + userId + "'");

        // Get the private chats
        Iterable<PrivateChat> privateChats = privateChatRepository.getUserChats(userId);

        Iterable<PrivateChatDTO> privateChatsDTO = new ArrayList<>();
        for(PrivateChat chat : privateChats){
            ActorMetadata destinatary = chat.getDestData(userId);

            PrivateChatDTO chatDTO = new PrivateChatDTO();
            chatDTO.setPrivateChatId(chat.getPrivateChatId());
            chatDTO.setSenderId(userId);
            chatDTO.setDestinataryId(destinatary.getUserId());
            chatDTO.setReceiverName(destinatary.getName());
            chatDTO.setLastMessageDate(chat.getLastMessageDate());

            ((ArrayList<PrivateChatDTO>) privateChatsDTO).add(chatDTO);
        }
        

        return ResponseEntity.ok(privateChatsDTO);
    }
   
    // Retrieve messages from a private chat
    public ResponseEntity<?> getPrivateChat(long privateChatId){

        log.info(log_header + "Retrieving messages from private chat with id: '" + privateChatId + "'");


        // Get the private chat
        PrivateChat privateChat = privateChatRepository.findById(privateChatId).orElse(null);

        if(privateChat == null){
            log.error(log_header + "Error: Private chat with id: '" + privateChatId + "' not found");
            return null;
        }
        
        // Get the messages
        Iterable<PrivateMessage> messages = privateMessageRepository.findByPrivateChatId(privateChatId);

        return ResponseEntity.ok(messages);
    }

    // Scheduled task to clean all private chats - runs at midnight on the first of every month
    //@Scheduled(cron = "0 0 0 1 * ?")
    public void cleanChats(){
        log.info(log_header + "Cleaning all private chats");

        privateChatRepository.deleteAll();
    }

    
}
