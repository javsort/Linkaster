package com.linkaster.userService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.model.User;
import com.linkaster.userService.service.UserAuthenticatorService;
import com.linkaster.userService.service.UserHandlerService;

import lombok.extern.slf4j.Slf4j;

/*
 * This class is the controller for the User service.
 * It handles all incoming requests to the service.
 * Called by the Gateway to create, delete, update, and retrieve user information
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController implements APIUserController {

    // Services for handling user operations
    private final UserHandlerService userHandlerService;
    private final UserAuthenticatorService userAuthenticatorService;        // To implement (?)
    //private final UserCustomizerService userCustomizerService;              // To implement (?)
    //private final AuthorizationAgentService authorizationAgentService;      // To implement (?)

    // Constructor
    @Autowired
    public UserController(UserHandlerService userHandlerService, UserAuthenticatorService userAuthenticatorService/*UserCustomizerService userCustomizerService, AuthorizationAgentService authorizationAgentService*/) {
        this.userHandlerService = userHandlerService;
        this.userAuthenticatorService = userAuthenticatorService;
        //this.userCustomizerService = userCustomizerService;
        //this.authorizationAgentService = authorizationAgentService;

    }

    // Home endpoint
    @Override
    public String home() {
        return "Welcome to the User Service!";
    }

    // CreateUser endpoint
    @Override
    public String createUser(User userInfo, String roleName) {
        userHandlerService.createUser(userInfo, roleName);
        return "User created successfully!";
    }
    
    // DeleteUser endpoint
    @Override
    public void deleteUser(long user_id){
        userHandlerService.deleteUser(user_id);
    }
    
    // UpdateUser endpoint
    @Override
    public boolean updateUser(User userToUpdate){
        return userHandlerService.updateUser(userToUpdate);
    }
    
    // GetUser endpoint
    @Override
    public User getUser(Long id){
        return userHandlerService.getUser(id);
    }
    
    // GetAllUsers endpoint
    @Override
    public List<User> getAllUsers(){
        return userHandlerService.getAllUsers();
    }

    // GetUsersByRole endpoint
    @Override
    public List<User> getUsersByRole(String role){
        return userHandlerService.getUsersByRole(role);
    }

}
