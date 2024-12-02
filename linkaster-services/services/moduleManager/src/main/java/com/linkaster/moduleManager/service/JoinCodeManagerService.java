package com.linkaster.moduleManager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.dto.JoinModuleCreate;
import com.linkaster.moduleManager.model.Module;
import com.linkaster.moduleManager.repository.ModuleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class JoinCodeManagerService {

    @Autowired
    private ModuleRepository moduleRepository;

    private final String log_header = "JoinCodeManagerService --- ";

    public List<String> getModuleCodes() {
        log.info(log_header + "Fetching all module codes");
        // Fetch all modules and map them to their codes
        return moduleRepository.findAll()
                            .stream()
                            .map(Module::getModuleCode)
                            .collect(Collectors.toList());
    }



    public Module getModuleByCode(String code) {
        log.info(log_header + "Getting join code by ID: " + code);
        return moduleRepository.findByModuleCode(code);
    }

    public boolean validateJoinCode(String joinCode) {
        log.info(log_header + "Validating join code: " + joinCode);
    
        // Check if the join code exists in the repository
        boolean exists = moduleRepository.findByModuleCode(joinCode) != null;
    
        log.info(log_header + "Join code " + joinCode + " is " + (exists ? "valid" : "invalid"));
    
        return exists;
    }
    
    
    // INTERSERVICE COMMUNICATION - Called by the student service
    public boolean joinModuleByCode(JoinModuleCreate joinModuleCreate) {
        String joinCode = joinModuleCreate.getCode();
        long studentId = joinModuleCreate.getStudentId();

        log.info(log_header + "Student " + studentId + " attempting to join module with join code: " + joinCode);
    
        // Step 1: Validate the join code
        if (!validateJoinCode(joinCode)) {
            log.warn(log_header + "Invalid join code: " + joinCode);
            return false; // Join code is invalid
        }
    
        // Step 2: Retrieve the module
        Module module = moduleRepository.findByModuleCode(joinCode);
        if (module == null) {
            log.error(log_header + "Module not found for join code: " + joinCode);
            return false; // Module does not exist
        }
    
        // Step 3: Check if the student is already enrolled
        if (module.getStudentList().contains(studentId)) {
            log.info(log_header + "Student: " + studentId + " is already enrolled in module: \nId: '" + module.getModuleId() + "'\nModule Name: '" + module.getModuleName() + "'");
            return true; // Student is already enrolled
        }
    
        // Step 4: Add the student to the module
        module.getStudentList().add(studentId);
        moduleRepository.save(module); // Save the updated module
    
        log.info(log_header + "Student " + studentId + " successfully joined module: " + module.getModuleName());
        return true; // Successfully added the student
    }
        
}
