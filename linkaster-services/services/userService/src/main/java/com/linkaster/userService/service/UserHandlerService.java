package com.linkaster.userService.service;

import java.security.KeyPair;
import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.linkaster.userService.model.Role;
import com.linkaster.userService.model.User;
import com.linkaster.userService.repository.RoleRepository;
import com.linkaster.userService.repository.UserRepository;
import com.linkaster.userService.util.KeyMaster;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/*
 * This class is responsible for handling all user related operations.
 * It interacts with the UserRepository and RoleRepository.
 * Creates Users and assigns roles while also providing user admin management
 */
@Service
@Transactional
@Slf4j
public class UserHandlerService {

    // Repositories for User and Role
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // Create a new user
    public boolean createUser(User userInfo, String roleName) {
        log.info("Creating user... for:" + userInfo.getId());

        // Check if user already exists
        if (userRepository.existsById(userInfo.getId())) {
            log.error("User already exists");
            return false;
        }

        // Then check if the e-mail is valid
        if (isEmailValid(userInfo.getEmail())) {
            log.error("Invalid email");
            return false;
        }

        // Assign role
        Role toAssign = roleRepository.findByRole(roleName);
        if(toAssign == null) {
            log.error("Role does not exist in DB");
            return false;
        }

        // Encrypt password
        int encryptionStrength = 10;

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(encryptionStrength, new SecureRandom());

        String encryptedPassword = bCryptPasswordEncoder.encode(userInfo.getPassword());

        // Create new user
        User newUser = new User();
        newUser.setUsername(userInfo.getUsername());
        newUser.setPassword(encryptedPassword);
        newUser.setEmail(userInfo.getEmail());
        newUser.setRole(toAssign);

        // Generate Key Set -> through keyMaster
        try {
            KeyMaster KeyMaster = new KeyMaster();
            KeyPair keypair = KeyMaster.keyGenerator();

            newUser.setKeyPair(keypair);
            log.info("Key pair generated successfully for user: " + newUser.getId());

        } catch (Exception e) {
            log.error("Error generating key pair");
            return false;
        }
        
        // Once all is set, save the user
        userRepository.save(newUser);
    
        return true;
    }

    // Check if email is valid
    private boolean isEmailValid(String email) {

        // Verify if the email is part of @lancaster.ac.uk
        if (email == null) { return false; } 
        else if (email.contains("@")) {
            // Now check if its a lancaster.ac.uk email
            String domain =  email.substring(email.indexOf('@') + 1);

            // if all is valid, then continue
            if(domain.equals("lancaster.ac.uk")) {

                return true;
            }
        }

        return false;
    }

    
    // Delete user
    public void deleteUser(long user_id){
        log.info("Deleting user with id: '" + user_id + "'...");
        userRepository.deleteById(user_id);
    }


    // Update user
    public boolean updateUser(User userToUpdate){
        // Check if user already exists
        User user = userRepository.findById(userToUpdate.getId()).orElse(null);
        
        if (user != null) {
            log.info("User found with id: '" + user.getId() + "'. Updating now...");
            
            user.setUsername(userToUpdate.getUsername());
            user.setPassword(userToUpdate.getPassword());
            user.setEmail(userToUpdate.getEmail());
            user.setRole(userToUpdate.getRole());
            user.setKeyPair(userToUpdate.getKeyPair());

            userRepository.save(user);

            return true;

        } else {
            log.error("User requested to update is not in the DB, returning error....");
            return false;
        }
    }

    // Get user
    public User getUser(Long id){
        // Check if user already exists
        User user = userRepository.findById(id).orElse(null);
        
        if(user == null){
            log.error("User does not exist in DB");
            return null;
        } else {
            return user;
        }
    }

    // Get all users
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // Get users by role
    public List<User> getUsersByRole(String role){
        return userRepository.findByRole(role);
    }
}
