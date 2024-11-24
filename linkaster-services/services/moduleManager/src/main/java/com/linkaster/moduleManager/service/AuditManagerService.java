package com.linkaster.moduleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.model.ClassModule;
import com.linkaster.moduleManager.repository.ModuleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;

@Service
@Transactional
@Slf4j
public class AuditManagerService {

    private final String log_header = "AuditManagerService --- ";

    @Autowired
    private ModuleRepository moduleRepository;


    public List<String> getStudentsByModule(long moduleId) {
        log.info("Getting students for module {}", moduleId);
        // Logic to get students for a module

        return null; // Replace with actual implementation
    }


    // Called by the student service - INTERSERVICE COMMUNICATION
    public ResponseEntity<Iterable<Long>> getTeachersByStudent(String studentId) {
        // From all the modules the student is enrolled in, get the teachers (moduleOwnerId -> userId -> teacher)
        log.info(log_header + "Getting teachers for student: {}", studentId);

        List<Long> teachers = new ArrayList<>();

        moduleRepository.findAll().forEach(module -> {
            // Check if module is a class module
            if(module instanceof ClassModule){

                // Check if student is enrolled in this module
                if (module.getStudentList().contains(studentId)) {
                    log.info(log_header + "Student {} is enrolled in module {}", studentId, module.getId());
                    
                    // Get the teacher for this module and add to teachers list
                    teachers.add(module.getModuleOwnerId());
                }
            }
        });

        if (teachers.isEmpty()) {
            log.info(log_header + "No teachers found for student: {}", studentId);
            return ResponseEntity.notFound().build();

        } else {
            log.info(log_header + "Teachers found for student: {}", studentId);
            return ResponseEntity.ok(teachers);

        }
    }
}
