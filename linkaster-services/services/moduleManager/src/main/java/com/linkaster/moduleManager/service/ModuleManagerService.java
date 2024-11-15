package com.linkaster.moduleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.model.Module;
import com.linkaster.moduleManager.repository.ModuleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ModuleManagerService {

    @Autowired
    private ModuleRepository moduleRepository;

    public Module createModule(Module module) {
        log.info("Creating module: {}", module);
        //Generate a unique ID for the module
        module.setId(moduleRepository.count() + 1);

        //Check that the module does not already exist
        if (moduleRepository.existsById(module.getId())) {
            log.error("Module with ID {} already exists", module.getId());
            return null;
        }
        //Check that code is unique
        if (moduleRepository.findByCode(module.getCode()) != null) {
            log.error("Module with code {} already exists", module.getCode());
            return null;
        }
        //Check that date and time(start time and end time) is valid 
        // CHECK PROBLEM: A MODULE CAN HAVEMULTIPLE START AND END TIMES but they need to check that specfic stat time goes with specific end time
        /*if(module.getStartTime().isAfter(module.getEndTime())) {
            log.error("Start time is after end time");
            return null;
        }
        */

        createModuleChat(module.getId());
        createStudentChat(module.getId());

        // If all checks pass, save the module
        return moduleRepository.save(module);
    }

    public Module updateModule(long id, Module module) {
        log.info("Updating module: {}", module);

        //Check that the module exists
        if (!moduleRepository.existsById(id)) {
            log.error("Module with ID {} does not exist", id);
            return null;
        }
        //Check that code is unique or the same to the previous one
        if (moduleRepository.findByCode(module.getCode()) != null && moduleRepository.findByCode(module.getCode()).getId() != id) {
            log.error("Module with code {} already exists", module.getCode());
            return null;
        }
        //Check that date and time is valid
        // CHECK PROBLEM: A MODULE CAN HAVE MULTIPLE START AND END TIMES but they need to check that specfic stat time goes with specific end time
        /* 
            if(module.getStartTime().isAfter(module.getEndTime())) {
                log.error("Start time is after end time");
                return null;
            }
        */

        return moduleRepository.save(module);
    }

    public void deleteModule(long id) {
        log.info("Deleting module by ID: {}", id);
        // If it exists, delete the module
        moduleRepository.deleteById(id);
    }

    public boolean createStudentChat (long id) {
        log.info("Creating student chat for module {} and student {}", id);
        // Logic to create a student chat
        //Pass to message service
        return false; // Replace with actual implementation
    }

    public boolean createModuleChat (long id) {
        log.info("Creating Module chat for module {} and teacher {}", id);
        // Logic to create a teacher chat
        //Pass to message service
        return false; // Replace with actual implementation
    }

    public List<Module> getAllModules() {
        log.info("Getting all modules");
        return moduleRepository.findAll();
    }

    public Module getModuleById(long id) {
        log.info("Getting module by ID: {}", id);
        return moduleRepository.findById(id).orElse(null);
    }
   


}
