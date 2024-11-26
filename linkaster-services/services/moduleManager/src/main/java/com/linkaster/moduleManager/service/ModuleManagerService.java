package com.linkaster.moduleManager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.linkaster.moduleManager.dto.ModuleCreate;
import com.linkaster.moduleManager.model.ClassModule;
import com.linkaster.moduleManager.model.ClubModule;
import com.linkaster.moduleManager.model.EventModel;
import com.linkaster.moduleManager.model.Module;
import com.linkaster.moduleManager.repository.EventRepository;
import com.linkaster.moduleManager.repository.ModuleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ModuleManagerService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private EventRepository eventRepository;

    private final String log_header = "ModuleManagerService --- ";

    public Module createModule(ModuleCreate module) {
        log.info(log_header + "Creating module: " + module);

        //Check if exists
        Module existingModule = moduleRepository.findByCode(module.getModuleCode());

        //Check that the module does not already exist
        if (existingModule != null) {
            log.error(log_header + "Module with ID: '" +  existingModule.getId() + "'' already exists");
            return null;
        }

        Module newModule;

        // Create module from DTO
        switch (module.getType()) {
            case "class_module":
                // If the module is a class module
                newModule = ClassModule.builder()
                        .moduleCode(module.getModuleCode())
                        .name(module.getName())
                        .type(module.getType())
                        .teacherId(module.getTeacherId())
                        .teacherName(module.getTeacherName())
                        .studentList(module.getStudentIds().stream().map(Long::valueOf).collect(Collectors.toList()))
                        .events(module.getEvents())
                        .announcements(module.getAnnouncements())
                        .build();
                // If all checks pass, save the module
                moduleRepository.save(newModule);
                break;

            case "club_module":
                // If the module is a club module
                newModule = ClubModule.builder()
                        .moduleCode(module.getModuleCode())
                        .name(module.getName())
                        .type(module.getType())
                        .clubLeaderStudentId(module.getClubLeaderStudentId())
                        .clubLeader(module.getClubLeader())
                        .studentList(module.getStudentIds().stream().map(Long::valueOf).collect(Collectors.toList()))
                        .events(module.getEvents())
                        .announcements(module.getAnnouncements())
                        .build();
                // If all checks pass, save the module
                moduleRepository.save(newModule);
                break;

            default:
                log.error(log_header + "Invalid module type");
                return null;
        }

        // Create chats on the way out
        createModuleChat(newModule.getId());

        log.info(log_header + "Module successfully created: " + newModule);
        return newModule;
    }

    public boolean updateModule(long id, ModuleCreate module) {

        //Check that the module exists
        if (!moduleRepository.existsById(id)) {
            log.error(log_header + "Module with ID: " + id +" does not exist");
            return false;
        }
        
        Module existingModule = moduleRepository.findById(id).orElse(null);

        log.info(log_header + "Updating module: " + existingModule.getName());
        
        //Check if code changed and check if the new one is already in use
        Module tempModule = moduleRepository.findByCode(existingModule.getModuleCode());
        
        //Check that the module does not already exist
        if (tempModule != null) {
            // If a tempModule is returned, check if it is the same as the module being updated
            if (tempModule.getId() != existingModule.getId()) {
                log.error(log_header + "Module with code '" + existingModule.getModuleCode() +"' already exists");
                return false;
            }
        }

        moduleRepository.save(existingModule);
        return true;
    }

    public boolean deleteModule(long id) {

        //Check that the module exists
        if (!moduleRepository.existsById(id)) {
            log.error(log_header + "Module with ID: '"+ id +"' does not exist");
            return false;
        }

        log.info(log_header + "Deleting module by ID: " + id);
        // If it exists, delete the module
        moduleRepository.deleteById(id);
        return true;
    }

    // Ping messaging service to create a chat for the module
    public boolean createModuleChat (long id) {
        log.info(log_header + "Creating Module chat for module: " + id);
        // Logic to create a teacher chat
        //Pass to message service
        return true; // Replace with actual implementation
    }

    public List<Module> getAllModules() {
        log.info(log_header + "Getting all modules");
        return moduleRepository.findAll();
    }

    public Module getModuleById(long id) {
        log.info(log_header + "Getting module by ID: " + id);
        return moduleRepository.findById(id).orElse(null);
    }

    public EventModel getModuleEvents(long id) {
        log.info(log_header + "Getting events for module: " + id);
        // Logic to get events for a module
        eventRepository.findByModuleId(id);
        return null; // Replace with actual implementation
    }

    public EventModel getEventsByUserId(long userId) {
        log.info(log_header + "Getting events for user: " + userId);
        // Logic to get events for a module by user
        eventRepository.findByUserId(userId);
        return null; // Replace with actual implementation
    }
   
}
