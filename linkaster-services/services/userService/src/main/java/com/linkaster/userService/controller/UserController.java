package com.linkaster.userService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.dto.ProfileInfoDTO;
import com.linkaster.userService.dto.UserRegistration;
import com.linkaster.userService.dto.message.PrivateChatReg;
import com.linkaster.userService.dto.message.PrivateChatSeedDTO;
import com.linkaster.userService.model.User;
import com.linkaster.userService.service.AuthorizationAgentService;
import com.linkaster.userService.service.UserAuthenticatorService;
import com.linkaster.userService.service.UserCustomizerService;
import com.linkaster.userService.service.UserHandlerService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/*
 * This class is the controller for the User service.
 * It handles all incoming requests to the service.
 * Called by the Gateway to create, delete, update, and retrieve user information
 */
@Slf4j
@RestController
public class UserController implements APIUserController {

    // Services for handling user operations
    private final UserHandlerService userHandlerService;       // To implement (?)
    private final UserCustomizerService userCustomizerService;              // To implement (?)
    private final AuthorizationAgentService authorizationAgentService;      // To implement (?)

    private final String log_header = "UserController --- ";

    // Constructor
    @Autowired
    public UserController(UserHandlerService userHandlerService, UserAuthenticatorService userAuthenticatorService, UserCustomizerService userCustomizerService, AuthorizationAgentService authorizationAgentService) {
        this.userHandlerService = userHandlerService;
        this.userCustomizerService = userCustomizerService;
        this.authorizationAgentService = authorizationAgentService;

    }

    // Home endpoint
    @Override
    public String home() {
        return "Welcome to the User Service!";
    }

    // CreateUser endpoint -> ADMIN ACCESS ONLY
    @Override
    public String createUser(@ModelAttribute UserRegistration regRequest, @PathVariable String roleName) {
        userHandlerService.createUser(regRequest, roleName);
        return "User created successfully!";
    }
    
    // DeleteUser endpoint
    @Override
    public void deleteUser(@PathVariable long user_id){
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
        log.info(log_header + "Getting all users...");
        return userHandlerService.getAllUsers();
    }

    // GetUsersByRole endpoint
    @Override
    public List<User> getUsersByRole(String role){
        return userHandlerService.getUsersByRole(role);
    }

    @Override
    public ResponseEntity<PrivateChatReg> retrieveDataForChats(@RequestBody PrivateChatSeedDTO seed){
        return authorizationAgentService.retrieveDataForChats(seed);
    }

    // GetProfile endpoint
    @Override
    public ProfileInfoDTO getStudentProfile(HttpServletRequest request){
        return userCustomizerService.getProfileStudent(request);
    }

    // GetProfile endpoint
    @Override
    public ProfileInfoDTO getTeacherProfile(HttpServletRequest request){
        return userCustomizerService.getProfileTeacher(request);
    }

}
