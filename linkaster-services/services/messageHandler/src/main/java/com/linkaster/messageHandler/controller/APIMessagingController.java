package com.linkaster.messageHandler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linkaster.messageHandler.model.group.GroupChatRegDTO;
import com.linkaster.messageHandler.model.p2p.PrivateChat;
import com.linkaster.messageHandler.model.p2p.PrivateMessage;

import jakarta.servlet.http.HttpServletRequest;


@RequestMapping("/api/message")
public interface APIMessagingController {

    @GetMapping("")
    public String home();

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllPrivateChats")
    public ResponseEntity<Iterable<PrivateChat>> getAllPrivateChats();

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllPrivateMessages")
    public ResponseEntity<Iterable<PrivateMessage>> getAllPrivateMessages();

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getPrivateChat/{id}")
    public ResponseEntity<PrivateChat> getPrivateChat(@PathVariable Long id);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getPrivateMessage/{id}")
    public  ResponseEntity<PrivateMessage> getPrivateMessage(@PathVariable Long id);

    /*
     * Establishing Websocket -> Called before establishing websocket for either private or group chat
    */
    @GetMapping("/establishSocket")
    public String establishSocket(HttpServletRequest request);

    
    /*
     * Private Messaging
     */
    // Called before establishing websocket -> Sample of all private chats with minimal information for the user
    @GetMapping("/private/all")
    public ResponseEntity<?> getUsersPrivateChats(HttpServletRequest request);

    // Called simultaneously as the websocket is established
    @GetMapping("/private/{chatId}")
    public ResponseEntity<?> getUserPrivateChat(@PathVariable Long chatId);

    /*
     * Group Messaging
     */
    // Called before establishing websocket -> Sample of all group chats with minimal information for the user

    // !!!! ONLY TO BE CALLED FROM MODULE MANAGER SERVICE
    @PostMapping("/group/create")
    public ResponseEntity<?> createGroupChat(HttpServletRequest request, @RequestBody GroupChatRegDTO groupChatReg) throws Exception;

    @GetMapping("/group/all")
    public ResponseEntity<?> getUsersGroupChats(HttpServletRequest request);
 
    // Called simultaneously as the websocket is established
    @GetMapping("/group/{chatId}")
    public ResponseEntity<?> getUserGroupChat(@PathVariable Long chatId);

    
    
}
