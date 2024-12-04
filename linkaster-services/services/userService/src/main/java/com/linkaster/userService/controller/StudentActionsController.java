package com.linkaster.userService.controller;


import javax.swing.text.html.parser.Entity;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.EntityResponse;

import com.linkaster.userService.dto.TeacherDTO;
import com.linkaster.userService.dto.UserRegistration;
import com.linkaster.userService.model.User;
import com.linkaster.userService.repository.UserRepository;
import com.linkaster.userService.service.UserHandlerService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/user/student")    // This is the base path for the student actions -> EXCLUSIVE TO THIS CLASS SET (TeacherActionsController & StudentActionsController)
public class StudentActionsController implements APIUserActionsController {

    private final UserHandlerService userHandlerService;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    
    private final String log_header = "StudentActionsController --- ";

    @Value("${address.logicGateway.url}")
    private String logicGatewayAddress;

    @Autowired
    public StudentActionsController(UserHandlerService userHandlerService, UserRepository userRepository) {
        this.userHandlerService = userHandlerService;
        this.userRepository = userRepository;
    }
    
    @Override
    public String home() {
        return "Welcome Student!";
    }

    @Override
    public String register(UserRegistration userInfo, String role) {
        return "Student registration successful!";
    }

    @Override
    public String getProfile() {
        return "Student profile retrieved!";
    }

    @Override
    public String assignModuleManager(HttpServletRequest request) {
        String userEmail = request.getAttribute("userEmail").toString();
        log.info(log_header + "Request received to get assign ClubStudent to Module with email: '" + userEmail + "'");
        return userHandlerService.assignModuleManager(userEmail);   
    }

    /*
     * Student only requests
     */

    // Join a module based on module_code
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/joinModule/{module_code}")
    public String joinModule(@PathVariable("module_code") String module_code){
        return "Student joined module!";
    }

    // Get all the teachers of the student based on studentId - INTERSERVICE COMMUNICATION
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/getTeachers")
    public Iterable<TeacherDTO> getStudentTeachers(HttpServletRequest request){
        String userEmail = request.getAttribute("userEmail").toString();
        log.info(log_header + "Request received to get student's teachers with the following email: '" + userEmail + "'");
        return userHandlerService.getStudentTeachers(request);   
    }

    
}
