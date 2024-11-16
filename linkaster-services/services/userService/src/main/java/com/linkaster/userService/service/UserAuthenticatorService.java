package com.linkaster.userService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Service;

import com.linkaster.userService.dto.AuthUser;
import com.linkaster.userService.model.User;
import com.linkaster.userService.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


// Proving who you are - Authentication

/*
 * This class is responsible for authenticating users.
 * ONLY CLASS with no authorization required. Directly pinged by logicGateway to verify user credentials.
 */
@Service
@Transactional
@Slf4j
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class UserAuthenticatorService {

    // Repository for User
    @Autowired
    private UserRepository userRepository;

    private final String log_header = "UserAuthenticatorService: ";

    // Authenticate user -> Check if user exists and if password is correct
    public boolean authenticateUser(String userEmail, String password) {
        log.info(log_header + "Authenticating user: " + userEmail);

        // Check if user exists
        if (userRepository.findByEmail(userEmail) == null) {
            log.error(log_header + "The user: '" + userEmail + "' does not exist");
            return false;
        }

        // Check if password is correct
        if (!userRepository.findByEmail(userEmail).getPassword().equals(password)) {
            log.error(log_header + "Incorrect password for user: '" + userEmail + "'");
            return false;
        }

        return true;
    }

    // Slightly different to the one found in user handler. User handler is for admin tasks, this for retrieving necessary info for JWT token
    public AuthUser getAuthenticatedUser(String userEmail) {
        log.info("Getting user: " + userEmail);

        User toGet = userRepository.findByEmail(userEmail);

        return new AuthUser(toGet.getId(), toGet.getUsername(), toGet.getRole().getRole());

    }
    
}
