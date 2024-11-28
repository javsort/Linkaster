package com.linkaster.messageHandler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.linkaster.messageHandler.dto.GroupMessageDTO;
import com.linkaster.messageHandler.dto.PrivateMessageDTO;
import com.linkaster.messageHandler.model.GroupMessage;
import com.linkaster.messageHandler.model.PrivateMessage;
import com.linkaster.messageHandler.service.PrivateMessagingManagerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WebSocketMessageController {

    private final String log_header = "APIWebSocketMessageController --- ";

    private final PrivateMessagingManagerService privateMessagingManagerService;

    @Autowired
    public WebSocketMessageController(PrivateMessagingManagerService privateMessagingManagerService) {
        this.privateMessagingManagerService = privateMessagingManagerService;
    }

    @MessageMapping("/sendPrivate")
    @SendTo("/topic/private/{chatId}")
    public PrivateMessage sendPrivateMessage(PrivateMessageDTO messageObj, SimpMessageHeaderAccessor headerAccessor) {
        // Get the sender Id from the JWT token
        long senderId = Long.parseLong(headerAccessor.getSessionAttributes().get("userId").toString());
        String chatId = headerAccessor.getSessionAttributes().get("chatId").toString();

        // BizLogic here
        log.info(log_header + "Handling private message for chat Id: " + chatId);

        PrivateMessage encryptedMessage = privateMessagingManagerService.sendMessage(messageObj, senderId);

        if(encryptedMessage == null) {
            log.error(log_header + "Error sending message!");
            throw new RuntimeException("Error sending message!");
        }

        return encryptedMessage;
    }

    @MessageMapping("/sendGroup")
    @SendTo("/topic/group/{groupId}")
    public GroupMessage sendPublicMessage(GroupMessageDTO messageObj, SimpMessageHeaderAccessor headerAccessor){
        
        
        GroupMessage encryptionPair = new GroupMessage();

        return encryptionPair;
    }
}
