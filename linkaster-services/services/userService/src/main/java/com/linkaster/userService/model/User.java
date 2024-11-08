package com.linkaster.userService.model;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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

    @ManyToAny(fetch= FetchType.EAGER)
    @JoinTable(
        name= "roles",
        joinColumns= @JoinColumn(name= "user_id"),
        inverseJoinColumns= @JoinColumn(name= "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Column(name="keyPair")
    private KeyPair keyPair;

    @Column(name="privateKey")
    private PrivateKey privateKey;

    @Column(name="publicKey")
    private PublicKey publicKey;
}
