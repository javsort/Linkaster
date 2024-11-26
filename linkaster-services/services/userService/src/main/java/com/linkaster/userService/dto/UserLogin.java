package com.linkaster.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

// DTO for user login -> what the user sends to the server

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserLogin {
    private String userEmail;
    private String password;
    
}

