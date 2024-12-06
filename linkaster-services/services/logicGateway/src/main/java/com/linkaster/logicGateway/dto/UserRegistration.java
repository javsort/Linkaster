package com.linkaster.logicGateway.dto;

import lombok.AllArgsConstructor;
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
// DTO for user registration -> what the user sends to the server
public class UserRegistration {
    private String name;
    private String surname;
    
    private String userEmail;
    private String password;   
    
    // Student only fields
    private String studentId;
    private Integer year;
    private String course;

    // Teacher only fields
    private String subject;

}


