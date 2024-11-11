package com.linkaster.userService.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.service.UserAuthenticatorService;

@RestController
@RequestMapping("/api/auth")
public class UserAuthenticatorController implements APIAuthenticationController {


    private final UserAuthenticatorService userAuthenticatorService;

    @Autowired
    public UserAuthenticatorController(UserAuthenticatorService userAuthenticatorService){
        this.userAuthenticatorService = userAuthenticatorService;
    }

    @Override
    public ResponseEntity<?> login(String username, String password){

        if(userAuthenticatorService.authenticateUser(username, password)){
            Map<String, String> response = new HashMap<>();

            response.put("message", "User authenticated");
            response.put("role", userAuthenticatorService.getUserRole(username));
            return ResponseEntity.ok(response);
        }



        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
