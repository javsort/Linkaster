package com.linkaster.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 *  Title: UserRegistration.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class UserRegistration {
    // DTO for user registration -> what the user sends to the server
    private String name;
    private String surname;
    
    private String userEmail;
    private String password;   
    
    // Student only fields
    private String studentId;
    private String year;
    private String studyProg;

    // Teacher only fields
    private String subject;

}


