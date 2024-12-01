package com.linkaster.messageHandler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.messageHandler.dto.GroupMessageDTO;
import com.linkaster.messageHandler.dto.MessageRetrieval;
import com.linkaster.messageHandler.dto.PrivateMessageDTO;
import com.linkaster.messageHandler.model.p2p.PrivateChat;
import com.linkaster.messageHandler.repository.PrivateChatRepository;
import com.linkaster.messageHandler.repository.PrivateMessageRepository;
import com.linkaster.messageHandler.security.JwtTokenProvider;
import com.linkaster.messageHandler.service.GroupMessagingManagerService;
import com.linkaster.messageHandler.service.PrivateMessagingManagerService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MessagingController implements APIMessagingController {

    private final String log_header = "MessagingController --- ";

    private final JwtTokenProvider jwtTokenProvider;
    private final PrivateChatRepository privateChatRepository;
    private final PrivateMessageRepository privateMessageRepository;
    private final PrivateMessagingManagerService privateMessagingManagerService;
    private final GroupMessagingManagerService groupMessagingManagerService;

    @Autowired
    public MessagingController(JwtTokenProvider jwtTokenProvider, PrivateChatRepository privateChatRepository, PrivateMessageRepository privateMessageRepository, PrivateMessagingManagerService privateMessagingManagerService, GroupMessagingManagerService groupMessagingManagerService){
        this.jwtTokenProvider = jwtTokenProvider;
        this.privateChatRepository = privateChatRepository;
        this.privateMessageRepository = privateMessageRepository;
        this.privateMessagingManagerService = privateMessagingManagerService;
        this.groupMessagingManagerService = groupMessagingManagerService;
    }
    
    @Override
    public String home(){
        return "Welcome to the Messaging Service!";
    }

    @Override
    public ResponseEntity<?> getUsersPrivateChats(HttpServletRequest request){
        log.info(log_header + "Retrieving all private chats for user: " + request.getHeader("Authorization"));
        
        // Get user id from session attribute
        long userId = request.getAttribute("id") != null ? Long.parseLong(request.getAttribute("id").toString()) : 0;

        if(userId == 0){
            return ResponseEntity.badRequest().body("Invalid user id. Please provide a valid user id in the request.");
        }

        return privateMessagingManagerService.getUsersPrivateChats(userId);
    }

    @Override
    public ResponseEntity<Iterable<PrivateChat>> getAllPrivateChats(){
        List<PrivateChat> privateChats = privateChatRepository.findAll();

        return ResponseEntity.ok(privateChats);
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

    // Authorize and return WebSocket Address
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

        return String.format( "Socket authorized for user with id: '" + userId +"''. Connect to WebSocket URL: ws://message-handler-service/ws with your JWT token.");
    }

    @Override
    public ResponseEntity<?> getPrivateChat(Long chatId){
        return privateMessagingManagerService.getPrivateChat(chatId);
    }
}
