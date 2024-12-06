package com.linkaster.userService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.security.KeyPair;



 /*
 *  Title: User.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {
    /*
    * This is the User entity class. It represents a user in the system.
    * It is an abstract class that is extended by the Student and Teacher classes.
    */

    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name= "first_name", unique= true, nullable= false)
    private String firstName;

    @Column(name= "last_name", unique= true, nullable= false)
    private String lastName;

    @Column(name= "password")
    private String password;

    @Column(name= "email", unique= true, nullable= false)
    private String email;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="role", referencedColumnName="role")
    private Role role;

    // Public Key as a Base64-encoded string
    @Lob
    @Column(name = "public_key", columnDefinition = "LONGTEXT")
    private String publicKey;

    // Private Key as a Base64-encoded string
    @Lob
    @Column(name = "private_key", columnDefinition = "LONGTEXT")
    private String privateKey;
}
