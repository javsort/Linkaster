package com.linkaster.messageHandler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.messageHandler.dto.PrivChatDTO;
import com.linkaster.messageHandler.model.group.GroupChatRegDTO;
import com.linkaster.messageHandler.model.p2p.PrivateChat;
import com.linkaster.messageHandler.model.p2p.PrivateMessage;
import com.linkaster.messageHandler.repository.PrivateChatRepository;
import com.linkaster.messageHandler.repository.PrivateMessageRepository;
import com.linkaster.messageHandler.security.JwtTokenProvider;
import com.linkaster.messageHandler.service.GroupMessagingManagerService;
import com.linkaster.messageHandler.service.PrivateMessagingManagerService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/*
 *  Title: MessagingController.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
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

    /*
     * ADMIN REQUESTS
     */
    @Override
    public ResponseEntity<Iterable<PrivateChat>> getAllPrivateChats(){
        List<PrivateChat> privateChats = privateChatRepository.findAll();

        return ResponseEntity.ok(privateChats);
    }

    @Override
    public ResponseEntity<Iterable<PrivateMessage>> getAllPrivateMessages(){
        List<PrivateMessage> privateMessages = privateMessageRepository.findAll();

        return ResponseEntity.ok(privateMessages);
    }

    @Override
    public ResponseEntity<PrivateChat> getPrivateChat(@PathVariable Long id){
        PrivateChat requestedChat = privateChatRepository.findById(id).orElse(null);

        if(requestedChat == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(requestedChat);
    }

    @Override
    public ResponseEntity<PrivateMessage> getPrivateMessage(@PathVariable Long id){
        PrivateMessage requestedMessage = privateMessageRepository.findById(id).orElse(null);

        if(requestedMessage == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(requestedMessage);
    }



    /*
     * Mortal being requests
     */

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

    /*
     * Private Messaging
     */
    // Create chat from email request
    @Override
    public ResponseEntity<?> createPrivateChat(HttpServletRequest request, @RequestBody PrivChatDTO email){
        log.info(log_header + "Creating private chat with email: " + email.getDestEmail());

        return privateMessagingManagerService.createPrivateChat(request, email.getDestEmail());
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

    // UserVersion of request
    @Override
    public ResponseEntity<?> getUserPrivateChat(Long chatId){
        return privateMessagingManagerService.getPrivateChat(chatId);
    }

    /*
     * Group Messaging
     */
    // !!!! ONLY TO BE CALLED FROM MODULE MANAGER SERVICE
    @Override
    public ResponseEntity<Boolean> createGroupChat(HttpServletRequest request,  @RequestBody GroupChatRegDTO groupChatReg) throws Exception{
        log.info(log_header + "Creating group chat for module: " + groupChatReg.getModuleName());

        return ResponseEntity.ok(groupMessagingManagerService.createChatForModule(groupChatReg));

    }

    @Override
    public ResponseEntity<?> getUsersGroupChats(HttpServletRequest request){
        log.info(log_header + "Retrieving all group chats for user: " + request.getHeader("Authorization"));
        
        // Get user id from session attribute
        long userId = request.getAttribute("id") != null ? Long.parseLong(request.getAttribute("id").toString()) : 0;

        if(userId == 0){
            return ResponseEntity.badRequest().body("Invalid user id. Please provide a valid user id in the request.");
        }

        return groupMessagingManagerService.getGroupChats4User(userId);
    }

    // UserVersion of request
    @Override
    public ResponseEntity<?> getUserGroupChat(Long chatId){
        return groupMessagingManagerService.getUserGroupChat(chatId);
    }
}
