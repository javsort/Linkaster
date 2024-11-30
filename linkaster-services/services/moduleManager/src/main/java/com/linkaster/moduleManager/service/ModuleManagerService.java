package com.linkaster.moduleManager.service;

import java.util.ArrayList;
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

        // Check if the module already exists
        Module existingModule = moduleRepository.findByModuleCode(module.getModuleCode());

        if (existingModule != null) {
            log.error(log_header + "Module with code: '" + existingModule.getModuleCode() + "' already exists");
            return null;
        }

        Module newModule;

        // Create module from DTO based on its type
        switch (module.getType()) {
            case "class_module":
                // If the module is a class module
                newModule = ClassModule.builder()
                        .moduleCode(module.getModuleCode())
                        .name(module.getName())
                        .type(module.getType())
                        .teacherId(module.getTeacherId())
                        .teacherName(module.getTeacherName())
                        .studentList(module.getStudentsId().stream().map(Long::valueOf).collect(Collectors.toList()))
                        .build();
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
                        .studentList(module.getStudentsId().stream().map(Long::valueOf).collect(Collectors.toList()))
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
        // Check that the module exists
        if (!moduleRepository.existsById(id)) {
            log.error(log_header + "Module with ID: " + id + " does not exist");
            return false;
        }
    
        Module existingModule = moduleRepository.findById(id).orElse(null);
    
        log.info(log_header + "Updating module: " + existingModule.getName());
    
        // Check if code changed and check if the new one is already in use
        Module tempModule = moduleRepository.findByModuleCode(module.getModuleCode());
    
        if (tempModule != null && tempModule.getId() != existingModule.getId()) {
            log.error(log_header + "Module with code '" + existingModule.getModuleCode() + "' already exists");
            return false;
        }
    
        // Apply changes from the ModuleCreate DTO to the existing module
        existingModule.setModuleCode(module.getModuleCode());
        existingModule.setName(module.getName());
    
        // Handle teacher or student leader assignments based on module type
        if (existingModule instanceof ClassModule) {
            // Cast to ClassModule to update teacher-specific fields
            ClassModule classModule = (ClassModule) existingModule;
            classModule.setTeacherId(module.getTeacherId());
            classModule.setTeacherName(module.getTeacherName());
        } else if (existingModule instanceof ClubModule) {
            // Cast to ClubModule to update club leader-specific fields
            ClubModule clubModule = (ClubModule) existingModule;
            clubModule.setClubLeaderStudentId(module.getClubLeaderStudentId());
            clubModule.setClubLeader(module.getClubLeader());
        }
    
        // Update the list of students (common for both class and club modules)
        existingModule.setStudentList(module.getStudentsId().stream().map(Long::valueOf).collect(Collectors.toList()));
    
        // Save the updated module
        moduleRepository.save(existingModule);
        return true;
    }
    

    public boolean deleteModule(long id) {
        // Check that the module exists
        if (!moduleRepository.existsById(id)) {
            log.error(log_header + "Module with ID: '" + id + "' does not exist");
            return false;
        }

        log.info(log_header + "Deleting module by ID: " + id);
        moduleRepository.deleteById(id);
        return true;
    }

    // Ping messaging service to create a chat for the module
    public boolean createModuleChat(long id) {
        log.info(log_header + "Creating Module chat for module: " + id);
        // Implement logic to create a chat for the module here (e.g., using a messaging service API)
        return true; // Placeholder implementation
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
