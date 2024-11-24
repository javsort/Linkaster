package com.linkaster.userService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linkaster.userService.dto.UserLogin;
import com.linkaster.userService.dto.UserRegistration;

/*
 * This interface defines the API endpoints for the Authentication Controller.
 */

@RequestMapping("/api/auth")
public interface APIAuthenticationController {
    
    // Authenticate endpoint -> pinged by logicGateway
    @PostMapping("/{user_type}/login")
    public ResponseEntity<?> authenticate(@RequestBody UserLogin loginRequest, @PathVariable("user_type") String user_type);

    // Register endpoint -> pinged by logicGateway
    @PostMapping("/{user_type}/register")
    public ResponseEntity<?> register(@RequestBody UserRegistration regRequest, @PathVariable("user_type") String user_type);

}
