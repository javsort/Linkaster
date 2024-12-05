package com.linkaster.messageHandler.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfo {
    @Column(name = "user_id")
    private long userId;

    
    @Lob
    @Column(name = "public_key", columnDefinition = "LONGTEXT")
    private String publicKey;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

//    @Column(name = "status")
//    private String status;
}
