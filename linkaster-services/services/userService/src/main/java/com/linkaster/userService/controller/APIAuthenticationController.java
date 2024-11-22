package com.linkaster.userService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.linkaster.userService.dto.UserLogin;

/*
 * This interface defines the API endpoints for the Authentication Controller.
 */
public interface APIAuthenticationController {
    
    // Authenticate endpoint -> pinged by logicGateway
    //@PostMapping("/{user_type}/login")
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody UserLogin loginRequest);

}
