package com.linkaster.moduleManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.model.ClassModule;
import com.linkaster.moduleManager.model.ClubModule;
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
        moduleRepository.findById(moduleId).ifPresent(module -> {
            // Check if the module is a ClassModule
            if (module instanceof ClassModule) {
                log.info(log_header + "Module {} is a class module", moduleId);

                // Add student IDs from the class module to the list
                studentIds.addAll(((ClassModule) module).getStudentList());
            }
            // Check if the module is a ClubModule
            else if (module instanceof ClubModule) {
                log.info(log_header + "Module {} is a club module", moduleId);

                // Add student IDs from the club module to the list
                studentIds.addAll(((ClubModule) module).getStudentList());
            }
            // Handle unknown module types
            else {
                log.error(log_header + "Module {} is not a class or club module", moduleId);
            }
        });

        // Log and return the result
        if (studentIds.isEmpty()) {
            log.info(log_header + "No students found for module: {}", moduleId);
            return Collections.emptyList(); // Return an empty list if no students are found
        } else {
            log.info(log_header + "Found {} students for module: {}", studentIds.size(), moduleId);
            //Transform student IDs to student names
            return studentIds;
        }
    }


    // Called by the student service - INTERSERVICE COMMUNICATION
    public ResponseEntity<Iterable<Long>> getTeachersByStudent(long studentId) {
        log.info(log_header + "Getting teachers for student: {}", studentId);
    
        List<Long> teachers = new ArrayList<>();
    
        moduleRepository.findAll().forEach(module -> {
            if (module instanceof ClassModule) {
                ClassModule classModule = (ClassModule) module;
    
                // Check if student is enrolled in this module
                if (classModule.getStudentList().contains(studentId)) {
                    log.info(log_header + "Student {} is enrolled in module {}", studentId, module.getId());
    
                    // Get the teacher for this module and add to teachers list
                    teachers.add(classModule.getModuleOwnerId());
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
