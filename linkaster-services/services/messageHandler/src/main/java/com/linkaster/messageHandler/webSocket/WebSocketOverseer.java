package com.linkaster.messageHandler.webSocket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.linkaster.messageHandler.dto.ActorMetadata;
import com.linkaster.messageHandler.message.p2p.PrivateChat;
import com.linkaster.messageHandler.repository.PrivateChatRepository;
import com.linkaster.messageHandler.security.JwtTokenProvider;

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
public class WebSocketOverseer implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final PrivateChatRepository privateChatRepository;

    @Autowired
    public WebSocketOverseer(JwtTokenProvider jwtTokenProvider, PrivateChatRepository privateChatRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.privateChatRepository = privateChatRepository;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
            
        // Get the token from the request
        String token = request.getHeaders().getFirst("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return false;   // Reject the connection
        }

        // Validate the token
        token = token.substring(7);

        if(!jwtTokenProvider.validateToken(token)){
            return false;   // Reject the connection
        }

        // Get userId from token
        long userId = Long.parseLong(jwtTokenProvider.getClaims(token, "userId"));

        // Retrieve chatID from headers
        String chatId = request.getHeaders().getFirst("chatId");
        if (chatId == null) {
            return false;   // Reject the connection
        }


        // Get PrivateChat obj
        PrivateChat privateChat = privateChatRepository.findById(chatId).orElse(null);
        if (privateChat == null) {
            return false;   // Reject the connection
        }

        // Return dest data -> public key
        ActorMetadata dest = privateChat.getDestData(userId);
        attributes.put("recipientPublicKey", dest.getPublicKey());
        attributes.put("recipientId", dest.getUserId());
        attributes.put("chatId", chatId);

        return true;    // Accept the connection
    }

    @Override
    public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
        // Do nothing
    }

    
        
}
