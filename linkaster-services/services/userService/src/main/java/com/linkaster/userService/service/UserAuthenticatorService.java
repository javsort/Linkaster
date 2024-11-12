package com.linkaster.userService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.userService.dto.AuthUser;
import com.linkaster.userService.model.User;
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

    // Authenticate user -> Check if user exists and if password is correct
    public boolean authenticateUser(String username, String password) {
        log.info("Authenticating user: " + username);
        // Check if user exists
        if (userRepository.findByUsername(username) == null) {
            log.error("The user: '" + username + "' does not exist");
            return false;
        }

        // Check if password is correct
        if (!userRepository.findByUsername(username).getPassword().equals(password)) {
            log.error("Incorrect password for user: '" + username + "'");
            return false;
        }

        return true;
    }

    // Slightly different to the one found in user handler. User handler is for admin tasks, this for retrieving necessary info for JWT token
    public AuthUser getAuthenticatedUser(String username) {
        log.info("Getting user: " + username);

        User toGet = userRepository.findByUsername(username);

        return new AuthUser(toGet.getId(), toGet.getUsername(), toGet.getRole().getRole());

    }
    
}
