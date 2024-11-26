package com.linkaster.messageHandler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.messageHandler.dto.GroupMessageDTO;
import com.linkaster.messageHandler.dto.MessageRetrieval;
import com.linkaster.messageHandler.dto.PrivateMessageDTO;
import com.linkaster.messageHandler.security.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MessagingController implements APIMessagingController {

    private final String log_header = "MessagingController --- ";
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public MessagingController(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @Override
    public String home(){
        return "Welcome to the Messaging Service!";
    }

    @Override
    public String getAllMessages(){

        return "All Messages Retrieved!";
    }

    @Override
    public String getMessage(){

        return "Message Retrieved!";
    }

    @Override
    public String sendMessage(PrivateMessageDTO messageToSend){
            
        return "Message Sent!";
    }

    @Override
    public String sendGroupMessage(GroupMessageDTO messageToSend){

        
        return "Message Sent!";
    }

    @Override
    public String retrieveMessage(MessageRetrieval toRetrieve){

        
        return "Message Retrieved!";
    }

    // Authorize and return WebSocket Addr
    @Override
    public String establishSocket(HttpServletRequest request){
        log.info(log_header + "Establishing WebSocket connection for user: " + request.getHeader("Authorization"));
        // Get Auth Header
        String authHeader = request.getHeader("Authorization");

        // Check if authHeader is valid
        if(authHeader == null || authHeader.isEmpty()){
            return "Unauthorized! Please provide a valid JWT token in the Authorization header.";
        }

        String token = authHeader.substring(7);
        // Check if token is valid
        if(!jwtTokenProvider.validateToken(token)){
            return "Unauthorized! Please provide a valid JWT token in the Authorization header.";
        }

        String userId = jwtTokenProvider.getClaims(token, "id");


        return String.format( "Socket authorized! Connect to WebSocket URL: ws://message-handler-service/ws with your JWT token.");
    }

    // Private Messaging Actions


    // Group Messaging Actions
}
