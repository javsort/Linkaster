package com.linkaster.userService.model;

import java.security.KeyPair;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {

    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name= "username", unique= true, nullable= false)
    private String username;

    @Column(name= "password")
    private String password;

    @Column(name= "email", unique= true, nullable= false)
    private String email;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name= "role_id")
    private Role role;

    @Column(name="keyPair")
    private KeyPair keyPair;
}
