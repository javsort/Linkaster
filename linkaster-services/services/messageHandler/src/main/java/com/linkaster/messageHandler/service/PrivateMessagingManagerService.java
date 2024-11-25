package com.linkaster.messageHandler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.messageHandler.dto.PrivateChatReg;
import com.linkaster.messageHandler.message.p2p.PrivateChat;
import com.linkaster.messageHandler.repository.PrivateChatRepository;
import com.linkaster.messageHandler.repository.PrivateMessageRepository;
import com.linkaster.messageHandler.util.KeyMaster;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/*
 * Will handle all private messaging related services
 */

@Service
@Transactional
@Slf4j
public class PrivateMessagingManagerService {


    @Autowired
    private PrivateMessageRepository privateMessageRepository;

    
    @Autowired
    private PrivateChatRepository privateChatRepository;

    @Autowired
    private KeyMaster keyMaster;

    private final String log_header = "PrivateMessagingManagerService --- ";

    // Constructor
    public PrivateMessagingManagerService(PrivateMessageRepository privateMessageRepository, PrivateChatRepository privateChatRepository, KeyMaster keyMaster) {
        this.privateMessageRepository = privateMessageRepository;
        this.privateChatRepository = privateChatRepository;
        this.keyMaster = keyMaster;
    }

    public boolean createPrivateChat(PrivateChatReg newChat){
        log.info(log_header + "Creating a new private chat between userId: '" + newChat.getUser1().getUserId() + "'' and userId:'" + newChat.getUser2().getUserId() + "'");

        // Create a new private chat
        PrivateChat newPrivateChat = new PrivateChat();
        newPrivateChat.setUser1(newChat.getUser1());
        newPrivateChat.setUser2(newChat.getUser2());

        // Save the new chat
        privateChatRepository.save(newPrivateChat);

        return true;
    }

    
}
