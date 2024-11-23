package com.linkaster.userService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.linkaster.userService.dto.UserLogin;

/*
 * This interface defines the API endpoints for the Authentication Controller.
 */
public interface APIAuthenticationController {
    
    // Authenticate endpoint -> pinged by logicGateway
    //@PostMapping("/login")
    @PostMapping("/{user_type}/login")
    public ResponseEntity<?> authenticate(@RequestBody UserLogin loginRequest, @PathVariable("user_type") String user_type);

}
