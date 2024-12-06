package com.linkaster.messageHandler.webSocket;

import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkaster.messageHandler.dto.GroupMessageDTO;
import com.linkaster.messageHandler.dto.GroupMessageReturnDTO;
import com.linkaster.messageHandler.dto.PrivateMessageDTO;
import com.linkaster.messageHandler.model.p2p.PrivateMessage;
import com.linkaster.messageHandler.security.JwtTokenProvider;
import com.linkaster.messageHandler.service.GroupMessagingManagerService;
import com.linkaster.messageHandler.service.PrivateMessagingManagerService;

import lombok.extern.slf4j.Slf4j;

/*
 * I am the overseer of the web socket.
 * I will manage the web socket connection.
 * I will be responsible for managing the web socket connection.
 * I will honor the web socket connection.
 * I will fulfill the web socket connection.
 * I will be the web socket connection.
 * This is my purpose.
 * 
 */

 /*
 *  Title: WebSocketOverseer.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Component
@Slf4j
public class WebSocketOverseer extends TextWebSocketHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final PrivateMessagingManagerService privateMessagingManagerService;
    private final GroupMessagingManagerService groupMessagingManagerService;

    private static final Map<Long, WebSocketSession> activeSessions = Collections.synchronizedMap(new HashMap<>());

    private final String log_header = "WebSocketOverseer --- ";

    @Autowired
    public WebSocketOverseer(JwtTokenProvider jwtTokenProvider, PrivateMessagingManagerService privateMessagingManagerService, GroupMessagingManagerService groupMessagingManagerService) {
        log.info(log_header + "THE WEB SOCKET OVERSEER HAS RISEN!");
        this.jwtTokenProvider = jwtTokenProvider;
        this.privateMessagingManagerService = privateMessagingManagerService;
        this.groupMessagingManagerService = groupMessagingManagerService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(log_header + "Connection established. Awaiting AUTH message...");
        session.getAttributes().put("authenticated", false); // Default to unauthenticated
    }
    
    private boolean handleAuthentication(WebSocketSession session, Map<String, String> payload) throws Exception {
        String type = payload.get("type");
        log.info(log_header + "Handling authentication message of type: " + type);

        // First, authenticate token, then assign chat-specific attributes
        String token = payload.get("token");
        long userId = Long.parseLong(jwtTokenProvider.getClaims(token, "id"));
        log.info(log_header + "User id: " + userId);
        log.info(log_header + "Websocket session attributes: " + session.getAttributes());
        
        if (token == null) {
            log.error(log_header + "Missing token in AUTH message.");
            session.close(CloseStatus.BAD_DATA);
            return false;
        }

        if (!jwtTokenProvider.validateToken(token)) {
            log.error(log_header + "The token provided is invalid: '" + token + "'");
            session.close(CloseStatus.BAD_DATA);
            return false;
        }

        switch (type){
            case "PRIVATE_AUTH":
                log.info(log_header + "Private Authentication header detected. Handling private chat authentication...");
                return handlePrivateAuthentication(session, payload, userId);

            case "GROUP_AUTH":
                log.info(log_header + "Group Authentication header detected. Handling group chat authentication...");
                return handleGroupAuthentication(session, payload, userId);

            default:
                log.error(log_header + "Invalid protocol message type for authentication: " + type);
                session.close(CloseStatus.BAD_DATA);
                return false;
        }
    }

    private boolean handlePrivateAuthentication(WebSocketSession session, Map<String, String> payload, long userId) throws Exception {
        String chatId = payload.get("chatId");

        long longChatId = Long.parseLong(chatId);

        // Recipient id must be available for the rest of operations
        long recipientId = privateMessagingManagerService.authenticateChatAccess(userId, longChatId);

        if (recipientId == -1) {
            log.error(log_header + "User not authorized for chatId: " + chatId);
            session.close(CloseStatus.BAD_DATA);
            return false;
        }

        log.info(log_header + "User authenticated successfully for chatId: " + chatId);
        session.getAttributes().put("authenticated", true);
        session.getAttributes().put("userId", userId);
        session.getAttributes().put("chatId", chatId);
        session.getAttributes().put("recipientId", recipientId);
        return true;
    }

    private boolean handleGroupAuthentication(WebSocketSession session, Map<String, String> payload, long userId) throws Exception {
        String chatId = payload.get("chatId");
        long longChatId = Long.parseLong(chatId);

        if (chatId == null) {
            log.error(log_header + "Missing chatId in AUTH message.");
            session.close(CloseStatus.BAD_DATA);
            return false;
            
        }

        for (int i = 0; payload.size() > i; i++) {
            log.info(log_header + "Payload key: " + payload.keySet().toArray()[i] + " value: " + payload.values().toArray()[i]);
        }

        // Call GroupmessagingManagerService to authenticate user
        if (!groupMessagingManagerService.authenticateChatAccess(userId, longChatId)) {
            log.error(log_header + "User not authorized for chatId: " + chatId);
            session.close(CloseStatus.BAD_DATA);
            return false;
        }

        log.info(log_header + "User authenticated successfully for chatId: " + chatId);
        session.getAttributes().put("authenticated", true);
        session.getAttributes().put("userId", userId);
        session.getAttributes().put("chatId", chatId);
        // Add groupChat Attributes
        return true;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            log.info(log_header + "Message received: " + message.getPayload());
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> payload = objectMapper.readValue(message.getPayload(), Map.class);

            if (!session.getAttributes().getOrDefault("authenticated", false).equals(true)) {
                // Authenticate, if successful
                if(handleAuthentication(session, payload)){
                    log.info(log_header + "User authenticated successfully. Connection established!");
                    
                    // Add the session to the active sessions
                    long connectedUserId = Long.parseLong(session.getAttributes().get("userId").toString());

                    activeSessions.put(connectedUserId, session);

                } else {
                    log.error(log_header + "Error authenticating user. Closing connection...");
                    session.close(CloseStatus.BAD_DATA);
                    return;
                }
                
                return;
            }

            // Process regular chat messages if already authenticated
            log.info(log_header + "Processing chat message: " + payload);

            // Process the message
            String messageType = payload.get("type");
            long senderId = Long.parseLong(session.getAttributes().get("userId").toString());

            switch (messageType) {
                case "PRIVATE":
                    log.info(log_header + "Handling private message...");
                    processPrivate(session, payload, senderId);
                    
                    break;

                case "GROUP":
                    log.info(log_header + "Handling group message...");
                    processPublic(session, payload, senderId);
                    break;
                default:
                    log.error(log_header + "Invalid message type: " + messageType);
                    session.close(CloseStatus.BAD_DATA);
            }
        } catch (Exception e) {
            log.error(log_header + "Error: " + e.getMessage(), e);
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    /*
     * I will process the public message.
     */
    public void processPrivate(WebSocketSession session, Map<String, String> payload, long senderId) throws Exception {
        // Get the chatId from the session -> if private chat
        long privateChatId = Long.parseLong(session.getAttributes().get("chatId").toString());
                    
        // Convert to DTO -> better for handling on the service layer
        PrivateMessageDTO incPrivate = new PrivateMessageDTO();
        incPrivate.setPrivateChatId(privateChatId);
        incPrivate.setMessage(payload.get("message"));
        
        log.info(log_header + "Sending message to PrivateMessagingManagerService...");
        PrivateMessage toBroadcast = privateMessagingManagerService.sendMessage(incPrivate, senderId);

        // Send the message
        if(toBroadcast != null){
            log.info(log_header + "Message processed successfully! Broadcasting to recipient...");

            // Broadcast the message to the recipient
            broadcastToRecipient(toBroadcast);

        } else {
            log.error(log_header + "Error sending message!");
            session.close(CloseStatus.BAD_DATA);
            throw new RuntimeException("Error sending message!");
        }
    }

    public void broadcastToRecipient(PrivateMessage message) throws Exception {
        long recipientId = message.getReceiverId();
        WebSocketSession recipientSession = activeSessions.get(recipientId);

        if (recipientSession == null) {
            log.error(log_header + "Recipient is not connected. Message will not be sent.");
            return;
        }

        if(recipientSession.isOpen()){
            /*
             * For presentation -> decrypt with user's private
             */
            // String decryptedMssg = privateMessagingManagerService.getDecoded(privateChatId, message.getEncryptedMessage(), recipientId);


            log.info(log_header + "Recipient is connected. Sending message...");
            // Send the message to the recipient
            PrivateMessageDTO messageToSend = new PrivateMessageDTO();
            messageToSend.setPrivateChatId(message.getPrivateChat().getPrivateChatId());
            messageToSend.setMessage(message.getEncryptedMessage());
            
            ObjectMapper mapper = new ObjectMapper();
            String messageJson = mapper.writeValueAsString(messageToSend);
            recipientSession.sendMessage(new TextMessage(messageJson));
        } else {
            log.error(log_header + "Recipient is not connected. Message will not be sent.");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(log_header + "Connection closed. Status: " + status);
        // Remove the session from the active sessions
        long disconnUserId = Long.parseLong(session.getAttributes().get("userId").toString());
        activeSessions.remove(disconnUserId);
    }

    /*
     * I will process the public message.
     */
    public void processPublic(WebSocketSession session, Map<String, String> payload, long senderId) throws Exception {
        // Get the chatId from the session -> if group chat
        long groupChatId = Long.parseLong(session.getAttributes().get("chatId").toString());
                    
        // Convert to DTO -> better for handling on the service layer
        GroupMessageDTO incPublic = new GroupMessageDTO();
        incPublic.setGroupChatId(groupChatId);
        incPublic.setMessage(payload.get("message"));
        incPublic.setSenderId(senderId);
        
        log.info(log_header + "Sending message to GroupMessagingManagerService...");
        GroupMessageReturnDTO toBroadcast = groupMessagingManagerService.sendMessage(incPublic);

        // Send the message
        if(toBroadcast != null){
            log.info(log_header + "Message processed successfully! Broadcasting to group's connected users...");

            // Broadcast the message to the group
            broadcastToGroup(toBroadcast);

        } else {
            log.error(log_header + "Error sending message!");
            session.close(CloseStatus.BAD_DATA);
            throw new RuntimeException("Error sending message!");
        }
    
    }
    
    public void broadcastToGroup(GroupMessageReturnDTO message) throws Exception {
        long groupChatId = message.getGroupChatId();
        String messageContent = message.getMessage();
        long senderId = message.getSenderId();
        String senderName = message.getSenderName();
        Date timestamp = message.getTimestamp();
        
        // Get all connected users
        for (Map.Entry<Long, WebSocketSession> entry : activeSessions.entrySet()) {
            long userId = entry.getKey();
            WebSocketSession userSession = entry.getValue();
            
             // Check if the user is a member of the group
            if(groupMessagingManagerService.authenticateChatAccess(userId, groupChatId) && userId != senderId){
                if(userSession.isOpen()){
                    log.info(log_header + "User with id: " + userId + " is a member of the group. Sending message...");
                    // Send the message to the user
                    GroupMessageReturnDTO messageToSend = new GroupMessageReturnDTO(messageContent, groupChatId, senderId, senderName, timestamp);
                    ObjectMapper mapper = new ObjectMapper();
                    String messageJson = mapper.writeValueAsString(messageToSend);
                    userSession.sendMessage(new TextMessage(messageJson));
                    log.info(log_header + "Message sent to user with id: " + userId);

                } else {
                    log.error(log_header + "User with id: " + userId + " is not connected. Message will not be sent.");

                }
            }
        }
    }
}
