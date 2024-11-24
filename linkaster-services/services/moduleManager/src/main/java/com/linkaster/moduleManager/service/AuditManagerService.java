package com.linkaster.moduleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.dto.TeacherDTO;
import com.linkaster.moduleManager.repository.ModuleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

@Service
@Transactional
@Slf4j
public class AuditManagerService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Value("${address.logicGateway.url}")
    private String logicGatewayAddress;


    public List<String> getStudentsByModule(long moduleId) {
        log.info("Getting students for module {}", moduleId);
        // Logic to get students for a module

        return null; // Replace with actual implementation
    }

    public ResponseEntity<Iterable<TeacherDTO>> getTeachersByStudent(String studentId) {
        log.info("Getting teachers for student {}", studentId);

        // Call userHandler to pick the user and get the teachers
        
        // Logic to get teachers for a student
        Iterable<TeacherDTO> teachers;
        
        // Make request to userHandler to get teachers
        
        return null;
    }


}
