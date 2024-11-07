package com.linkaster.userService.model;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name="role")
    private Role role;

    @Column(name="keyPair")
    private KeyPair keyPair;

    @Column(name="privateKey")
    private PrivateKey privateKey;

    @Column(name="publicKey")
    private PublicKey publicKey;
}
