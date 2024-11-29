package com.linkaster.messageHandler.webSocket;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.linkaster.messageHandler.model.ActorMetadata;
import com.linkaster.messageHandler.model.p2p.PrivateChat;
import com.linkaster.messageHandler.repository.PrivateChatRepository;
import com.linkaster.messageHandler.security.JwtTokenProvider;

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
public class WebSocketOverseer implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final PrivateChatRepository privateChatRepository;

    private final String log_header = "WebSocketOverseer --- ";

    @Autowired
    public WebSocketOverseer(JwtTokenProvider jwtTokenProvider, PrivateChatRepository privateChatRepository) {
        log.info(log_header + "THE WEB SOCKET OVERSEER HAS RISEN!");
        this.jwtTokenProvider = jwtTokenProvider;
        this.privateChatRepository = privateChatRepository;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
        log.info(log_header + "Before Handshake - Request: " + request.getURI());

        
        /*  ---     Testing on URI query STARTS

        // Retrieve userId from uri query
        URI uri = request.getURI();
        String query = uri.getQuery();

        Map<String, String> queryMap = Arrays.stream(query.split("&"))
                .map(s -> s.split("="))
                .collect(java.util.stream.Collectors.toMap(a -> a[0], a -> a[1]));

        String token = queryMap.get("Authorization");
        String chatId = queryMap.get("chatId");
    
        // Get the token from the request
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
        if (chatId == null) {
            log.error(log_header + "Bad Request: 'chatId' was not received as part of the uri. Conisder thyself rejected!");
            throw new RuntimeException("Bad Request: 'chatId' was not received as a header");   // Reject the connection
        }*/
        
        //  ---     Testing on URI query ENDS


        // CURRENTLY DISABLED FOR TESTING    
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

    @Override
    public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
        // Do nothing
    }

    
        
}
