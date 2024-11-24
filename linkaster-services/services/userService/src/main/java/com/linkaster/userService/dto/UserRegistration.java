package com.linkaster.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String year;
    private String studyProg;

    // Teacher only fields
    private String subject;

}


