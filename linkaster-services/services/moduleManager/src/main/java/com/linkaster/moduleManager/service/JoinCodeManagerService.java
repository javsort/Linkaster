package com.linkaster.moduleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.repository.ModuleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class JoinCodeManagerService {

    @Autowired
    private ModuleRepository moduleRepository;

    public String[] getAllCodes() {
        log.info("Getting all join codes");
        // Logic to get all join codes
        return new String[0]; // Replace with actual implementation
    }   


    public String getModuleByCode(String code) {
        log.info("Getting join code by ID: {}", code);
        // Logic to get a join code by ID
        return "Join code"; // Replace with actual implementation
    }

    public boolean validateJoinCode(String joinCode) {
        log.info("Validating join code: {}", joinCode);
        // Logic to validate a join code
        return false; // Replace with actual implementation
    }

    public void joinModuleByCode(String joinCode, long studentId) {
        log.info("Student {} joining module with join code {}", studentId, joinCode);
        // Logic to join a module using the join code
    }
}
