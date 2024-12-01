package com.linkaster.messageHandler.webSocket;

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

        // Call GroupmessagingManagerService to authenticate user


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
                    // Handle group message
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

    /*@Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
        log.info(log_header + "Before Handshake - Request: " + request.getURI());

        // Get the token from the request
        String token = request.getHeaders().getFirst("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            log.error(log_header + "Bad Request: Missing token. Conisder thyself rejected!");
            throw new RuntimeException("Bad Request: Missing token");   // Reject the connection
        }

        // Validate the token
        token = token.substring(7);

        if(!jwtTokenProvider.validateToken(token)){
            log.error(log_header + "Bad Request: Token is invalid. Conisder thyself rejected!");
            throw new RuntimeException("Bad Request: Token is invalid");   // Reject the connection
        }

        log.info(log_header + "The overseed token proofs itself worthy! \nGetting the userId from the token...");

        // Get userId from token
        long userId = Long.parseLong(jwtTokenProvider.getClaims(token, "id"));

        // Retrieve chatID from headers
        String chatId = request.getHeaders().getFirst("chatId");
        if (chatId == null) {
            log.error(log_header + "Bad Request: 'chatId' was not received as a header. Conisder thyself rejected!");
            throw new RuntimeException("Bad Request: 'chatId' was not received as a header");   // Reject the connection
        }

        log.info(log_header + "I oversee that: \nThe userId is: " + userId + " and, \nChatId is: " + chatId);

        // Get PrivateChat obj
        PrivateChat privateChat = privateChatRepository.findById(chatId).orElse(null);
        if (privateChat == null) {
            log.error(log_header + "Bad Request: private chat is non-existent. Conisder thyself rejected!");
            throw new RuntimeException("Bad Request: private chat is non-existent.");   // Reject the connection
        }

        log.info(log_header + "I oversee that: the chat is found! \nChecking if the user is part of the chat...");

        // Check if the user is part of the chat
        if (!privateChat.isUserInChat(userId)) {
            
            throw new RuntimeException("Bad Request: user requesting chat access is not part of the chat.");   // Reject the connection
        }

        log.info(log_header + "I oversee that: the user is part of the chat! \nGetting the recipient's public key...");

        // Return dest data -> public key
        ActorMetadata dest = privateChat.getDestData(userId);
        attributes.put("recipientPublicKey", dest.getPublicKey());
        attributes.put("recipientId", dest.getUserId());
        attributes.put("chatId", chatId);

        log.info(log_header + "Consider thyself overseen! \nRecipient's public key: " + dest.getPublicKey() + " and, \nRecipient's Id: " + dest.getUserId());

        return true;    // Accept the connection
    }

    /*@Override
    public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
        // Do nothing
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Listen for the initial protocol message
        session.getAttributes().put("authenticated", false); // Default to unauthenticated

        session.setTextMessageHandler(message -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> protocolMessage = mapper.readValue(message.getPayload(), Map.class);

                if ("AUTH".equals(protocolMessage.get("type"))) {
                    String token = protocolMessage.get("token");
                    String chatId = protocolMessage.get("chatId");

                    // Validate token
                    if (!jwtTokenProvider.validateToken(token)) {
                        session.close(CloseStatus.BAD_DATA);
                        return;
                    }

                    // Optionally validate chatId (e.g., via a service)
                    // Example: Check if the user has access to the chat ID
                    String userId = jwtTokenProvider.getClaims(token, "id");
                    if (!isValidChatAccess(userId, chatId)) {
                        session.close(CloseStatus.BAD_DATA);
                        return;
                    }

                    // Mark the session as authenticated
                    session.getAttributes().put("authenticated", true);
                    session.getAttributes().put("userId", userId);
                    session.getAttributes().put("chatId", chatId);
                } else {
                    session.close(CloseStatus.BAD_DATA);
                }
            } catch (Exception e) {
                log.error("Error during WebSocket authentication: " + e.getMessage());
                session.close(CloseStatus.SERVER_ERROR);
            }
        });
    }*/
    
        
}
