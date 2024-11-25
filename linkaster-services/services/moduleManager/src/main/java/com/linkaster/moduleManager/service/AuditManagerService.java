package com.linkaster.moduleManager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.model.ClassModule;
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
    public List<String> getStudentsByModule(long moduleId) {
        log.info(log_header + "Getting students for module: " + moduleId);
        
        List<String> students = new ArrayList<>();

        moduleRepository.findById(moduleId).ifPresent(module -> {
            // Check if module is a class module
            if(module instanceof ClassModule){
                log.info(log_header + "Module {} is a class module", moduleId);

                // Get the students for this module
                students.addAll(((ClassModule) module).getStudentList());
            }
        });
            // Check if module is a club module
            // if(module instanceof ClubModule){
            //     log.info(log_header + "Module {} is a club module", moduleId);

            //     // Get the students for this module

            //     // students.addAll(((ClubModule) module).getStudentList());
            // }
            //});

        if (students.isEmpty()) {
            log.info(log_header + "No students found for module: {}", moduleId);
            return null;

        } else {
            log.info(log_header + "Students found for module: {}", moduleId);
            return students;

        }
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
