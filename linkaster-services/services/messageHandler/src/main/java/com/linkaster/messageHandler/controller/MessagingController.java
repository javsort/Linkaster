package com.linkaster.messageHandler.controller;

import com.linkaster.messageHandler.dto.GroupMessageDTO;
import com.linkaster.messageHandler.dto.MessageRetrieval;
import com.linkaster.messageHandler.dto.PrivateMessageDTO;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MessagingController implements APIMessagingController {
    
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

    // Private Messaging Actions


    // Group Messaging Actions
}
