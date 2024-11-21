package com.linkaster.logicGateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for user login -> what the user sends to the server
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin {
    private String userEmail;
    private String password;
}

