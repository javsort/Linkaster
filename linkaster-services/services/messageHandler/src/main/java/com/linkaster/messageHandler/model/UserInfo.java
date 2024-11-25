package com.linkaster.messageHandler.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfo {
    private long userId;
    private String publicKey;
    private String name;
    private String email;
    private String role;
    private String status;
}
