package com.linkaster.userService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.userService.model.Role;
import com.linkaster.userService.model.User;
import com.linkaster.userService.repository.RoleRepository;
import com.linkaster.userService.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserHandlerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // Does need user
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

        User newUser = new User();
        newUser.setUsername(userInfo.getUsername());
        newUser.setPassword(userInfo.getPassword());
        newUser.setEmail(userInfo.getEmail());
        newUser.setRole(toAssign);

        userRepository.save(newUser);
    
        // Generate Key Set!!!!
        /* After validation, create user
        User newUser = new User(userInfo.getId(), userInfo.getUsername(), userInfo.getPassword(), userInfo.getEmail(), userInfo.getRole(), userInfo.getKeyPair(), userInfo.getPrivateKey(), userInfo.getPublicKey());
        newUser.setUsername();
        */

        return true;
    }

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

    
    // Could just request student id
    public void deleteUser(User userToDel){
        log.info("Deleting user: '" + userToDel.getId() + "'...");
        userRepository.delete(userToDel);
    }


    // Could just request student id
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
            user.setPrivateKey(userToUpdate.getPrivateKey());
            user.setPublicKey(userToUpdate.getPublicKey());

            userRepository.save(user);

            return true;
        } else {
            log.error("User requested to update is not in the DB, returning error....");
            return false;
        }
    }

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

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(String role){
        return userRepository.findByRole(role);
    }
}
