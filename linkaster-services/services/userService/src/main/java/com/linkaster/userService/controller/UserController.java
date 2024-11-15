package com.linkaster.userService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.model.User;
import com.linkaster.userService.service.UserAuthenticatorService;
import com.linkaster.userService.service.UserHandlerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController implements APIUserController {

    private final UserHandlerService userHandlerService;
    private final UserAuthenticatorService userAuthenticatorService;        // To implement (?)
    //private final UserCustomizerService userCustomizerService;              // To implement (?)
    //private final AuthorizationAgentService authorizationAgentService;      // To implement (?)

    @Autowired
    public UserController(UserHandlerService userHandlerService, UserAuthenticatorService userAuthenticatorService/*UserCustomizerService userCustomizerService, AuthorizationAgentService authorizationAgentService*/) {
        this.userHandlerService = userHandlerService;
        this.userAuthenticatorService = userAuthenticatorService;
        //this.userCustomizerService = userCustomizerService;
        //this.authorizationAgentService = authorizationAgentService;

    }

    @Override
    public String home() {
        return "Welcome to the User Service!";
    }

    @Override
    public String createUser(User userInfo, String roleName) {
        userHandlerService.createUser(userInfo, roleName);
        return "User created successfully!";
    }
    
    @Override
    public void deleteUser(User userToDel){
        userHandlerService.deleteUser(userToDel);
    }
    
    @Override
    public boolean updateUser(User userToUpdate){
        return userHandlerService.updateUser(userToUpdate);
    }
    
    @Override
    public User getUser(Long id){
        return userHandlerService.getUser(id);
    }
    
    @Override
    public List<User> getAllUsers(){
        return userHandlerService.getAllUsers();
    }

    @Override
    public List<User> getUsersByRole(String role){
        return userHandlerService.getUsersByRole(role);
    }

}
