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

    public String home() {
        return "Welcome to the User Service!";
    }

    
    public String createUser(User userInfo){
        userHandlerService.createUser(userInfo);
        return "User created successfully!";
    }

    
    public void deleteUser(User userToDel){
        userHandlerService.deleteUser(userToDel);
    }

    
    public boolean updateUser(User userToUpdate){
        boolean result = userHandlerService.updateUser(userToUpdate);
        return result;
    }

    
    public User getUser(Long id){

        return userHandlerService.getUser(id);

        return ;
    }

    
    public User[] getAllUsers(){
        return new User[0];
    }

    public User[] getUsersByRole(String role){
        return new User[0];
    }

}
