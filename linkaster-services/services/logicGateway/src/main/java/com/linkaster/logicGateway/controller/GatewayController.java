package com.linkaster.logicGateway.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.logicGateway.service.GatewayAuthService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("")
@Slf4j
public class GatewayController implements APIGatewayController {

    @Autowired
    private GatewayAuthService gatewayAuthService;

    @Override
    public String home(){
        return "Logic gateway Home";
    }

    @Override
    public String status(){
        return "All good";
    }

    @Override
    public ResponseEntity<?> login(String username, String password){

        try {
            String token = gatewayAuthService.authenticateAndGenerateToken(username, password);

            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            log.error("Error while authenticating user", e);
            return ResponseEntity.status(401).body("Invalid credentials");
            
        }
    }


}
