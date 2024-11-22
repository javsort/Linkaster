package com.linkaster.messageHandler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.linkaster.messageHandler.dto.GroupMessageDTO;
import com.linkaster.messageHandler.dto.MessageDTO;
import com.linkaster.messageHandler.dto.MessageRetrieval;


public interface APIMessagingController {
    @GetMapping("/getAllMessages")
    public String getAllMessages();

    @GetMapping("/getMessage")
    public String getMessage();

    @PostMapping("/send")
    public String sendMessage(@RequestBody MessageDTO messageToSend);

    @PostMapping("/sendToGroup")
    public String sendGroupMessage(@RequestBody GroupMessageDTO messageToSend);

    @PostMapping("/retrieveMessage")
    public String retrieveMessage(@RequestBody MessageRetrieval toRetrieve);
    
    
}
