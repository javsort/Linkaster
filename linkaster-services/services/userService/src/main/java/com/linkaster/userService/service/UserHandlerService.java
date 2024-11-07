package com.linkaster.userService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.userService.model.User;
import com.linkaster.userService.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserHandlerService {

    @Autowired
    private UserRepository userRepository;

    public boolean createUser(User userInfo) {
        log.info("Creating user... for ");

        // Verify info (check lancaster email, check if user already exists)
        userRepository.findById(userInfo.getId());


        User newUser = new User();

        userRepository.save(newUser);

        return true;
    }
}
