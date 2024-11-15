package com.linkaster.userService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * This interface defines the API endpoints for the Authentication Controller.
 */
public interface APIAuthenticationController {
    
    // Authenticate endpoint -> pinged by logicGateway
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestParam String username, @RequestParam String password);
}
