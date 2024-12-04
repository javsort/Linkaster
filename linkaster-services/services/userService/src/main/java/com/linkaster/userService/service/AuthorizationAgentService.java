package com.linkaster.userService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.linkaster.userService.dto.message.PrivateChatReg;
import com.linkaster.userService.dto.message.PrivateChatSeedDTO;
import com.linkaster.userService.dto.message.UserInfo;
import com.linkaster.userService.model.User;
import com.linkaster.userService.repository.UserRepository;
import com.linkaster.userService.util.KeyMaster;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

// What you're allowed to do after proving who you are - Authorization

@Service
@Transactional
@Slf4j
public class AuthorizationAgentService {
    
    // Autowired components:
    private final KeyMaster keyMaster;
    private final UserRepository userRepository;
    
    private final String log_header = "AuthorizationAgentService --- ";

    @Autowired
    public AuthorizationAgentService(KeyMaster keyMaster, UserRepository userRepository) {
        this.keyMaster = keyMaster;
        this.userRepository = userRepository;
    }

    public ResponseEntity<PrivateChatReg> retrieveDataForChats(PrivateChatSeedDTO seed){
        log.info(log_header + "Recceived ping from messageHandler Service. Retrieving data for private chat creation.");

        // Create a new PrivateChatReg object based on seed
        long requesterId = seed.getRequesterId();
        String receiverEmail = seed.getDestEmail();

        // Verify credentials for both, and then create the UserInfo objects to fill PrivateChatReg

        // Requester - user 1 -> by user id
        User user1 = userRepository.findById(requesterId).get();

        if(user1 == null){
            log.error(log_header + "User with id: " + requesterId + " not found.");
            return ResponseEntity.badRequest().build();
        }

        UserInfo user1Info = new UserInfo();
        user1Info.setUserId(user1.getId());
        user1Info.setPublicKey(user1.getPublicKey());
        user1Info.setName(user1.getFirstName() + " " + user1.getLastName());
        user1Info.setEmail(user1.getEmail());
        user1Info.setRole(user1.getRole().getRole());

        // Receiver - user 2 -> by email
        User user2 = userRepository.findByEmail(receiverEmail);

        if(user2 == null){
            log.error(log_header + "User with email: " + receiverEmail + " not found.");
            return ResponseEntity.badRequest().build();
        }

        UserInfo user2Info = new UserInfo();
        user2Info.setUserId(user2.getId());
        user2Info.setPublicKey(user2.getPublicKey());
        user2Info.setName(user2.getFirstName() + " " + user2.getLastName());
        user2Info.setEmail(user2.getEmail());
        user2Info.setRole(user2.getRole().getRole());


        // Once verified and built, create PrivateChatReg object
        PrivateChatReg privateChatReg = new PrivateChatReg();
        privateChatReg.setUser1(user1Info);
        privateChatReg.setUser2(user2Info);

        return ResponseEntity.ok(privateChatReg);
    }

}
