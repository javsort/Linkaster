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
    
    private String email;
    private String password;   
}
