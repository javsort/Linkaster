package com.linkaster.userService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.dto.TeacherDTO;
import com.linkaster.userService.dto.UserRegistration;
import com.linkaster.userService.service.UserHandlerService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/student")
public class StudentActionsController implements APIUserActionsController {

    private final UserHandlerService userHandlerService;

    @Autowired
    public StudentActionsController(UserHandlerService userHandlerService) {
        this.userHandlerService = userHandlerService;
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

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/joinModule/{module_code}")
    public String joinModule(@PathVariable("module_code") String module_code){
        return "Student joined module!";
    }

    // Get all the teachers of the student based on studentId
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/getTeachers")
    public List<TeacherDTO> getStudentTeachers(HttpServletRequest request){
        String studentEmail = request.getAttribute("userEmail").toString();

        
        // Curr debugging
        TeacherDTO teacher = new TeacherDTO();
        List<TeacherDTO> teachers = List.of(teacher);

        return teachers;
    }

    
}
