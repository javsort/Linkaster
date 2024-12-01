package com.linkaster.moduleManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.dto.EventCreate;
import com.linkaster.moduleManager.dto.ModuleCreate;
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

    public Module createModule(ModuleCreate module, String creatorRole) {
        log.info(log_header + "Creating module: " + module);

        // Check if the module already exists
        if (moduleRepository.existsByModuleCode(module.getModuleCode())) {
            log.error(log_header + "Module with code: '" + module.getModuleCode() + "' already exists");
            return null;
        }

        log.info(log_header + "Module is new!\nCreating new module with code: " + module.getModuleCode());
        Module newModule;

        // Make the owner id from string to long
        long ownerId = Long.parseLong(module.getModuleOwnerId());

        // Create module from DTO based on its type
        switch (module.getType()) {
            case "class_module":
                // If the module is a class module
                log.info(log_header + "Creating class module");
                
                newModule = Module.builder()
                        .moduleOwnerId(ownerId)
                        .moduleOwnerName(module.getModuleOwnerName())
                        .moduleOwnerType(creatorRole)
                        .moduleName(module.getModuleName())
                        .moduleCode(module.getModuleCode())
                        .studentList(new ArrayList<>())
                        .type("class_module")
                        .build();
                moduleRepository.save(newModule);
                break;

            case "club_module":
                // If the module is a club module
                log.info(log_header + "Creating club module");

                newModule = Module.builder()
                        .moduleOwnerId(ownerId)
                        .moduleOwnerName(module.getModuleOwnerName())
                        .moduleOwnerType(creatorRole)
                        .moduleName(module.getModuleName())
                        .moduleCode(module.getModuleCode())
                        .studentList(new ArrayList<>())
                        .type("club_module")
                        .build();
                moduleRepository.save(newModule);
                break;

            default:
                log.error(log_header + "Invalid module type");
                return null;
        }

        // Create chat for the module
        createModuleChat(newModule.getId());

        log.info(log_header + "Module successfully created: " + newModule);

        return newModule;
    }

    public boolean updateModule(long id, ModuleCreate module) {
        // Check if the module exists
        if (!moduleRepository.existsById(id)) {
            log.error(log_header + "Module with ID: " + id + " does not exist");
            return false;
        }
    
        Module existingModule = moduleRepository.findById(id).orElse(null);
        if (existingModule == null) {
            log.error(log_header + "Module with ID: " + id + " could not be retrieved from the database");
            return false;
        }
    
        // Check if the new module code already exists in another module
        if (moduleRepository.existsByModuleCode(module.getModuleCode()) &&
            !existingModule.getModuleCode().equals(module.getModuleCode())) {
            log.error(log_header + "Module with code: '" + module.getModuleCode() + "' already exists");
            return false;
        }
    
        log.info(log_header + "Updating module: " + existingModule.getModuleName());
    
        // Apply changes from the ModuleCreate DTO to the existing module
        existingModule.setModuleCode(module.getModuleCode());
        existingModule.setModuleName(module.getModuleName());
        existingModule.setModuleOwnerId(Long.parseLong(module.getModuleOwnerId()));
        existingModule.setModuleOwnerName(module.getModuleOwnerName());
        existingModule.setModuleOwnerType(module.getType());
    
        // Save the updated module
        moduleRepository.save(existingModule);
        log.info(log_header + "Module successfully updated: " + existingModule);
    
        return true;
    }
        

    public boolean deleteModule(long id) {
        // Check if the module exists
        if (!moduleRepository.existsById(id)) {
            log.error(log_header + "Module with ID: '" + id + "' does not exist");
            return false;
        }
    
        log.info(log_header + "Deleting module with ID: " + id);
    
        try {
            moduleRepository.deleteById(id);
    
            // Verify deletion
            if (moduleRepository.existsById(id)) {
                log.error(log_header + "Module with ID: '" + id + "' could not be deleted");
                return false;
            }
    
            // Optional: Remove related resources (e.g., chats, events)
            //deleteModuleChat(id);  // Assuming you have a method to delete associated module chats
            //deleteModuleEvents(id);  // Assuming you have a method to delete associated module events
    
            log.info(log_header + "Module successfully deleted with ID: " + id);
            return true;
        } catch (Exception e) {
            log.error(log_header + "Error occurred while deleting module with ID: " + id, e);
            return false;
        }
    }
    

    // Ping messaging service to create a chat for the module
    public boolean createModuleChat(long id) {
        log.info(log_header + "Creating Module chat for module: " + id);
        // Implement logic to create a chat for the module here (e.g., using a messaging service API)
        return true; // Placeholder implementation
    }

    //Create a new event
    public EventModel createEvent(EventCreate eventCreate) {
        log.info(log_header + "Creating event: " + eventCreate);

        // Check if the module exists
        if (!moduleRepository.existsById(eventCreate.getModuleId())) {
            log.error(log_header + "Module with ID: " + eventCreate.getModuleId() + " does not exist");
            return null;
        }

    

        // Create the event
        EventModel newEvent = EventModel.builder()
                .name(eventCreate.getName())
                .room(eventCreate.getRoom())
                .date(new java.sql.Date(eventCreate.getDate().getTime()))
                .startTime(eventCreate.getStartTime())
                .endTime(eventCreate.getEndTime())
                .ownerId(eventCreate.getOwnerId())
                .moduleId(eventCreate.getModuleId())
                .build();

        // Save and return the event
        EventModel savedEvent = eventRepository.save(newEvent);
        log.info(log_header + "Event created successfully: " + savedEvent);
        return savedEvent;
    }

    public List<Module> getAllModules() {
        log.info(log_header + "Getting all modules");
        return moduleRepository.findAll();
    }

    public Module getModuleById(long id) {
        log.info(log_header + "Getting module by ID: " + id);
        return moduleRepository.findById(id).orElse(null);
    }

    public List<Module> getModulesByStudent(long studentId) {
        log.info(log_header + "Getting modules for student: " + studentId);
        List<Module> modules = new ArrayList<>();
        moduleRepository.findAllByStudentId(studentId).forEach(modules::add);
        return modules;
    }

    public List<EventModel> getModuleEvents(long id) {
        log.info(log_header + "Getting events for module: " + id);
        return eventRepository.findByModuleId(id);  // Return the actual events
    }

    public List<EventModel> getEventsByUserId(long userId) {
        log.info(log_header + "Getting events for user: " + userId);
        return eventRepository.findByOwnerId(userId);  // Return the actual events
    }
}
