/*
 *  Title: ModuleController.java
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */


package com.linkaster.moduleManager.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.moduleManager.dto.AnnouncementCreate;
import com.linkaster.moduleManager.dto.JoinModuleCreate;
import com.linkaster.moduleManager.dto.ModuleCreate;
import com.linkaster.moduleManager.dto.ModuleResponse;
import com.linkaster.moduleManager.dto.AnnouncementResponse;
import com.linkaster.moduleManager.dto.EventCreate;
import com.linkaster.moduleManager.model.Announcement;
import com.linkaster.moduleManager.model.Module;
import com.linkaster.moduleManager.service.AuditManagerService;
import com.linkaster.moduleManager.service.JoinCodeManagerService;
import com.linkaster.moduleManager.service.ModuleHandlerService;
import com.linkaster.moduleManager.service.ModuleManagerService;
import com.linkaster.moduleManager.service.TimetableIntegratorService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ModuleController implements APIModuleController {

    private final String log_header = "ModuleController --- ";

    private final ModuleHandlerService moduleHandlerService;
    private final ModuleManagerService moduleManagerService;
    private final AuditManagerService auditManagerService;
    private final JoinCodeManagerService joinCodeManagerService;
    private final TimetableIntegratorService timetableIntegratorService;

    @Autowired
    public ModuleController(ModuleHandlerService moduleHandlerService, ModuleManagerService moduleManagerService, AuditManagerService auditManagerService, JoinCodeManagerService joinCodeManagerService, TimetableIntegratorService timetableIntegratorService) {
        this.moduleHandlerService = moduleHandlerService;
        this.moduleManagerService = moduleManagerService;
        this.auditManagerService = auditManagerService;
        this.joinCodeManagerService = joinCodeManagerService;
        this.timetableIntegratorService = timetableIntegratorService;
    }


    @Override
    public String home() {
        return "Welcome to the Module Service!";
    }

    @Override
    public String status() {
        return "Module Service is up and running!";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<?> createModule(@RequestBody ModuleCreate module, HttpServletRequest request) {
        // Get role for module creation user type
        String creatorRole = request.getAttribute("role").toString();

        
        // Strip "ROLE_" from the role
        creatorRole = creatorRole.substring(5);

        // Create a new module
        log.info(log_header + "Creating new module: " + module + " calling moduleManagerService...");
        Module newModule = moduleManagerService.createModule(request, module, creatorRole);

        // Create a response entity
        if (newModule == null) { 
            return ResponseEntity.badRequest().body("Module creation failed");
        }

        ModuleResponse response = new ModuleResponse(
            newModule.getModuleId(),
            newModule.getModuleName(),
            newModule.getModuleCode(),
            newModule.getModuleOwnerName(),
            newModule.getModuleOwnerType(),
            newModule.getModuleOwnerId(),
            newModule.getStudentList(),
            newModule.getType()
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info(log_header + "Module created successfully: " + response + " returning response entity...");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteModule(@PathVariable long id) {
        log.info("Attempting to delete module with ID: {}", id);
        
        // Call the service method to delete the module
        boolean isDeleted = moduleManagerService.deleteModule(id);
        
        if (isDeleted) {
            return ResponseEntity.ok("Module deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found or deletion failed.");
        }
    }

    @Override
    public ResponseEntity<?> updateModule(@PathVariable long id, @RequestBody ModuleCreate module, HttpServletRequest request) {
        log.info(log_header + "Attempting to update module with ID: {}", id);
        long ownerId = (Long) request.getAttribute("id");
        String ownerName = (String) request.getAttribute("email");

        boolean isUpdated = moduleManagerService.updateModule(id, module, ownerId, ownerName);

        if (isUpdated) {
            log.info(log_header + "Module with ID {} updated successfully.", id);
            return ResponseEntity.ok("Module updated successfully.");
        } else {
            log.error(log_header + "Module with ID {} not found or update failed.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found or update failed.");
        }
    }
    @Override
    public List<Module> getAllModules() {
        return moduleManagerService.getAllModules();
    }

    @Override
    public Module getModuleById(@PathVariable long id) {
        log.info(log_header + "Fetching module with ID: {}", id);
        Module module = moduleManagerService.getModuleById(id);

        if (module == null) {
            log.error(log_header + "Module with ID {} not found.", id);
            return null;
        }

        log.info(log_header + "Module with ID {} retrieved successfully.", id);
        return module;
    }

    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<Long> getStudentsByModule(@PathVariable long moduleId) {
        // Example logic; replace with actual implementation
        log.info(log_header + "Getting students for module: {}", moduleId);
        return auditManagerService.getStudentsByModule(moduleId);
    }

    @Override
    public List<Module> getModulesByStudent(HttpServletRequest request) {
        log.info(log_header + "Getting modules for student: {}", request.getAttribute("id"));
        String studentIdString = (String) request.getAttribute("id");

        long studentId;

        try {
            studentId = Long.parseLong(studentIdString);
        } catch (NumberFormatException e) {
            log.error(log_header + "Invalid student ID: " + studentIdString);
            return Collections.emptyList();
        }
        
        return moduleManagerService.getModulesByStudent(studentId);
    }

    @Override
    public boolean joinModuleByCode(@RequestBody JoinModuleCreate joinModule, HttpServletRequest request) {
        log.info(log_header + "Joining module by code: {}", joinModule);
        return joinCodeManagerService.joinModuleByCode(joinModule, request);
    }

    @Override
    public void leaveModule(@RequestBody long moduleId, HttpServletRequest request) {
        // Logic for a student to leave a module
        String studentIdString = (String) request.getAttribute("id");

        long studentId;

        try {
            studentId = Long.parseLong(studentIdString);
        } catch (NumberFormatException e) {
            log.error(log_header + "Invalid student ID: " + studentIdString);
            return;
        }

        log.info(log_header + "Student: {} leaving module: {}", studentId, moduleId);
        moduleHandlerService.leaveModule(moduleId, studentId);
    }

    @Override
    public ResponseEntity<?> createAnnouncement(@RequestBody AnnouncementCreate announcement, HttpServletRequest request) {
        // Logic to create a new announcement
        log.info(log_header + "Creating new announcement: {}", announcement);

        // Implement the announcement creation logic here like createModule method
        Announcement newAnnouncement = moduleHandlerService.createAnnouncement(announcement, request);

        // Create a response entity
        if (newAnnouncement == null) {
            return ResponseEntity.badRequest().body("Announcement creation failed");
        }
        
        AnnouncementResponse response = new AnnouncementResponse(
            newAnnouncement.getId(),
            newAnnouncement.getMessage(),
            newAnnouncement.getOwnerId(),
            newAnnouncement.getOwnerName(),
            newAnnouncement.getTime(),
            newAnnouncement.getDate(), // Assuming date is converted to String
            newAnnouncement.getModuleId()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info(log_header + "Announcement created successfully: " + response + " returning response entity...");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    
    }


    @Override
    public ResponseEntity<?> deleteAnnouncement(@RequestBody long announcementId, @RequestBody long moduleId) {
        // Logic to delete an announcement
        log.info(log_header + "Deleting announcement: {} from module: {}", announcementId, moduleId);

        boolean isDeleted = moduleHandlerService.deleteAnnouncement(announcementId, moduleId);

        if (isDeleted) {
            return ResponseEntity.ok("Announcement deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Announcement not found or deletion failed.");
        }
    }

    @Override
    public Iterable<Announcement> getAllAnnouncementsByUserId(HttpServletRequest request) {
        // Logic to get all announcements by user
        String studentIdString = (String) request.getAttribute("id");

        long studentId;

        try {
            studentId = Long.parseLong(studentIdString);
        } catch (NumberFormatException e) {
            log.error(log_header + "Invalid student ID: " + studentIdString);
            return Collections.emptyList();
        }

        log.info(log_header + "Getting all announcements for user: {}", studentId);
        return moduleHandlerService.getAllAnnouncementsByStudent(studentId);
    }

    
    /*
     * Create an event instance that then the timetable will replicate
     */
    @Override
    public ResponseEntity<?> createEvent(@RequestBody EventCreate event, HttpServletRequest request) {
        // Logic to create a new event
        log.info(log_header + "Creating new event: '" + event.getName() + "' for module: " + event.getModuleId() + " creating Event and replicating on Timetable...");

        if(moduleManagerService.createEvent(event, request)){
            return ResponseEntity.ok("Event created successfully.");
        } else {
            return ResponseEntity.badRequest().body("Event creation failed.");
        }
    }






    /*
    @Override
    public Iterable<EventModel> getAllEventsByUserId(HttpServletRequest request) {
        // Logic to get all events by user
        String userIdString = (String) request.getAttribute("id");

        long userId;

        try {
            userId = Long.parseLong(userIdString);
        } catch (NumberFormatException e) {
            log.error(log_header + "Invalid user ID: " + userIdString);
            return Collections.emptyList();
        }

        log.info(log_header + "Getting all events for user: {}", userId);
        return moduleManagerService.getEventsByUserId(userId);
    }

    @Override
    public ResponseEntity<?> getAllEventsByModuleId(@PathVariable long id) {
        // Logic to get all events for a module
        log.info(log_header + "Getting events for module: {}", id);
        List<EventModel> events = moduleManagerService.getModuleEvents(id);

        if (events == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Events not found for module: " + id);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info(log_header + "Events retrieved successfully for module: {}", id);
        return new ResponseEntity<>(events, headers, HttpStatus.OK);
    }*/

    // Called by the timetable service - INTERSERVICE COMMUNICATION
    /*
    @Override
    public boolean updateTimetable(Integer time, Date date) {
        return timetableIntegratorService.updateTimetable(time, date);
    }
    */

    // Called by the student service - INTERSERVICE COMMUNICATION
    @Override
    public ResponseEntity<Iterable<Long>> getTeachersByStudent(HttpServletRequest request) {
        String studentIdString = (String) request.getAttribute("id");

        long studentId;

        try {
            studentId = Long.parseLong(studentIdString);
        } catch (NumberFormatException e) {
            log.error(log_header + "Invalid student ID: " + studentIdString);
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        
        log.info(log_header + "Getting teachers for student: {}", studentId);
        return auditManagerService.getTeachersByStudent(studentId);
    }

}
