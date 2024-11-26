package com.linkaster.messageHandler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linkaster.messageHandler.dto.GroupMessageDTO;
import com.linkaster.messageHandler.dto.MessageRetrieval;
import com.linkaster.messageHandler.dto.PrivateMessageDTO;

import jakarta.servlet.http.HttpServletRequest;


@RequestMapping("/api/messaging")
public interface APIMessagingController {

    @GetMapping("")
    public String home();

    @GetMapping("/getAllMessages")
    public String getAllMessages();

    @GetMapping("/getMessage")
    public String getMessage();

    @PostMapping("/send")
    public String sendMessage(@RequestBody PrivateMessageDTO messageToSend);

    @PostMapping("/sendToGroup")
    public String sendGroupMessage(@RequestBody GroupMessageDTO messageToSend);

    @PostMapping("/retrieveMessage")
    public String retrieveMessage(@RequestBody MessageRetrieval toRetrieve);

    @GetMapping("/establishSocket")
    public String establishSocket(HttpServletRequest request);
    
    
}
