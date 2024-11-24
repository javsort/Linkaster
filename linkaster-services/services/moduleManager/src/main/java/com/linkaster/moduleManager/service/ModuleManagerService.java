package com.linkaster.moduleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.dto.ModuleCreate;
import com.linkaster.moduleManager.model.ClassModule;
import com.linkaster.moduleManager.model.ClubModule;
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

    public Module createModule(ModuleCreate module) {
        log.info("Creating module: {}", module);

        //Check if exists
        Module existingModule = moduleRepository.findByCode(module.getModuleCode());

        //Check that the module does not already exist
        if (existingModule != null) {
            log.error("Module with ID {} already exists", existingModule.getId());
            return null;
        }

        //Check that date and time(start time and end time) is valid 
        // CHECK PROBLEM: A MODULE CAN HAVEMULTIPLE START AND END TIMES but they need to check that specfic stat time goes with specific end time
        /*if(module.getStartTime().isAfter(module.getEndTime())) {
            log.error("Start time is after end time");
            return null;
        }
        */

        Module newModule;

        // Create module from DTO
        switch (module.getType()) {
            case "class_module":
                // If the module is a class module
                newModule = ClassModule.builder()
                        .moduleCode(module.getModuleCode())
                        .name(module.getName())
                        .description(module.getDescription())
                        .type(module.getType())
                        .startTime(module.getStartTime())
                        .endTime(module.getEndTime())
                        .teacherId(module.getTeacherId())
                        .teacherName(module.getTeacherName())
                        .build();
                // If all checks pass, save the module
                moduleRepository.save(newModule);
                break;

            case "club_module":
                // If the module is a club module
                newModule = ClubModule.builder()
                        .moduleCode(module.getModuleCode())
                        .name(module.getName())
                        .description(module.getDescription())
                        .type(module.getType())
                        .startTime(module.getStartTime())
                        .endTime(module.getEndTime())
                        .clubLeaderStudentId(module.getClubLeaderStudentId())
                        .clubLeader(module.getClubLeader())
                        .build();
                // If all checks pass, save the module
                moduleRepository.save(newModule);
                break;

            default:
                log.error("Invalid module type");
                return null;
        }

        // Create chats on the way out
        createModuleChat(newModule.getId());

        log.info("Module successfully created: {}", newModule);
        return newModule;
    }

    public boolean updateModule(long id, ModuleCreate module) {

        //Check that the module exists
        if (!moduleRepository.existsById(id)) {
            log.error("Module with ID {} does not exist", id);
            return false;
        }
        
        Module existingModule = moduleRepository.findById(id).orElse(null);

        log.info("Updating module: {}", existingModule.getName());
        
        //Check if code changed and check if the new one is already in use
        Module tempModule = moduleRepository.findByCode(existingModule.getModuleCode());
        
        //Check that the module does not already exist
        if (tempModule != null) {
            // If a tempModule is returned, check if it is the same as the module being updated
            if (tempModule.getId() != existingModule.getId()) {
                log.error("Module with code {} already exists", existingModule.getModuleCode());
                return false;
            }
        }

        //Check that date and time is valid
        // CHECK PROBLEM: A MODULE CAN HAVE MULTIPLE START AND END TIMES but they need to check that specfic stat time goes with specific end time
        /* 
            if(module.getStartTime().isAfter(module.getEndTime())) {
                log.error("Start time is after end time");
                return null;
            }
        */

        moduleRepository.save(existingModule);
        return true;
    }

    public boolean deleteModule(long id) {

        //Check that the module exists
        if (!moduleRepository.existsById(id)) {
            log.error("Module with ID {} does not exist", id);
            return false;
        }

        log.info("Deleting module by ID: {}", id);
        // If it exists, delete the module
        moduleRepository.deleteById(id);
        return true;
    }

    // Ping messaging service to create a chat for the module
    public boolean createModuleChat (long id) {
        log.info("Creating Module chat for module {} and teacher {}", id);
        // Logic to create a teacher chat
        //Pass to message service
        return true; // Replace with actual implementation
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
