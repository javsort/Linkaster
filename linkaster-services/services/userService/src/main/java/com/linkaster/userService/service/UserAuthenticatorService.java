package com.linkaster.userService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.userService.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


// Proving who you are - Authentication
@Service
@Transactional
@Slf4j
public class UserAuthenticatorService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticateUser(String username, String password) {
        log.info("Authenticating user: " + username);
        // Check if user exists
        if (userRepository.findByUsername(username) == null) {
            log.error("User does not exist");
            return false;
        }

        // Check if password is correct
        if (!userRepository.findByUsername(username).getPassword().equals(password)) {
            log.error("Incorrect password");
            return false;
        }

        return true;
    }

    public String getUserRole(String username) {
        log.info("Getting role for user: " + username);
        return userRepository.findByUsername(username).getRole().getRole();
    }
    
}
