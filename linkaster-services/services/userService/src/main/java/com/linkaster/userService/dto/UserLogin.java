package com.linkaster.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


/*
 *  Title: UserLogin.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserLogin {
    // DTO for user login -> what the user sends to the server
    private String userEmail;
    private String password;
    
}

