package com.linkaster.userService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.model.User;
import com.linkaster.userService.service.UserHandlerService;
import com.linkaster.userService.service.AuthorizationAgentService;
import com.linkaster.userService.service.UserAuthenticatorService;
import com.linkaster.userService.service.UserCustomizerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController implements APIUserController {

    private final UserHandlerService userHandlerService;
    private final UserAuthenticatorService userAuthenticatorService;
    private final UserCustomizerService userCustomizerService;
    private final AuthorizationAgentService authorizationAgentService;

    @Autowired
    public UserController(UserHandlerService userHandlerService, UserAuthenticatorService userAuthenticatorService, UserCustomizerService userCustomizerService, AuthorizationAgentService authorizationAgentService) {
        this.userHandlerService = userHandlerService;
        this.userAuthenticatorService = userAuthenticatorService;
        this.userCustomizerService = userCustomizerService;
        this.authorizationAgentService = authorizationAgentService;

    }

    @ResponseStatus(HttpStatus.OK)
    public String home() {
        return "Welcome to the User Service!";
    }

    
    @ResponseStatus(HttpStatus.OK)
    public String createUser(){

        return "User created successfully!";
    }

    
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(){

    }

    
    @ResponseStatus(HttpStatus.OK)
    public boolean updateUser(){

        return true;
    }

    
    @ResponseStatus(HttpStatus.OK)
    public User getUser(){
        return new User();
    }

    
    @ResponseStatus(HttpStatus.OK)
    public User[] getAllUsers(){
        return new User[0];
    }

    
    @ResponseStatus(HttpStatus.OK)
    public User[] getUsersByRole(String role){
        return new User[0];
    }

}
