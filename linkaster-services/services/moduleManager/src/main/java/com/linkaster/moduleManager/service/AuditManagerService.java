package com.linkaster.moduleManager.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.model.Module;
import com.linkaster.moduleManager.repository.ModuleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AuditManagerService {

    private final String log_header = "AuditManagerService --- ";

    @Autowired
    private ModuleRepository moduleRepository;


    // Called by the student service - INTERSERVICE COMMUNICATION
    public List<Long> getStudentsByModule(long moduleId) {
        log.info(log_header + "Getting students for module: {}", moduleId);

        List<Long> studentIds = new ArrayList<>();

        // Fetch the module by ID
        Module module = moduleRepository.findById(moduleId).orElse(null);

        // Log and return the result
        if (module == null) {
            log.info(log_header + "Module with id: " + moduleId + " not found");
            return Collections.emptyList(); // Return an empty list if no students are found

        } else {
            
            int amountOfUsersInModule = module.getStudentList().size();

            log.info(log_header + "Found: " + amountOfUsersInModule + " students for module: " + moduleId);

            return module.getStudentList();
        }
    }


    // Called by the student service - INTERSERVICE COMMUNICATION
    public ResponseEntity<Iterable<Long>> getTeachersByStudent(long studentId) {
        log.info(log_header + "Getting teachers for student: {}", studentId);
    
        List<Long> teachers = new ArrayList<>();
    
        moduleRepository.findAll().forEach(module -> {
            if (module.getType().equals("class_module")) {
    
                // Check if student is enrolled in this module
                if (module.getStudentList().contains(studentId)) {
                    log.info(log_header + "Student {} is enrolled in module {}", studentId, module.getModuleId());
    
                    // Get the teacher for this module and add to teachers list
                    teachers.add(module.getModuleOwnerId());
                }
            }
        });
    
        if (teachers.isEmpty()) {
            log.info(log_header + "No teachers found for student: {}", studentId);
            return ResponseEntity.ok(Collections.emptyList()); // Or use `notFound()` if preferred
        } else {
            log.info(log_header + "Teachers found for student: {}", studentId);
            return ResponseEntity.ok(teachers);
        }
    }
}
