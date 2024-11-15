package com.linkaster.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


// This is a user that has been authenticated -> Necessary info for JWT
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthUser {
    
    private Long id;

    private String username;

    private String role; 
}
