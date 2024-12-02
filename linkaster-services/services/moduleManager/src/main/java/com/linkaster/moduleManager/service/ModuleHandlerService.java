package com.linkaster.moduleManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.dto.AnnouncementCreate;
import com.linkaster.moduleManager.model.Announcement;
import com.linkaster.moduleManager.model.Module;
import com.linkaster.moduleManager.repository.AnnouncementRepository;
import com.linkaster.moduleManager.repository.ModuleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ModuleHandlerService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    private final String log_header = "ModuleHandlerService --- ";

    // Admin Tasks

    public Announcement createAnnouncement(AnnouncementCreate announcementCreate) {
        long moduleId = announcementCreate.getModuleId();
        String message = announcementCreate.getMessage();
        long ownerId = announcementCreate.getOwner_id();
    
        log.info(log_header + "Creating announcement for module: " + moduleId);
    
        // Validate module existence
        Optional<Module> moduleOptional = moduleRepository.findById(moduleId);
        if (moduleOptional.isEmpty()) {
            log.error(log_header + "Module with ID " + moduleId + " not found.");
            throw new IllegalArgumentException("Module not found");
        }
    
        Module module = moduleOptional.get();
    
        // Create the announcement
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setMessage(message);
        newAnnouncement.setDate(new java.sql.Date(System.currentTimeMillis())); // Current date
        newAnnouncement.setTime(java.time.LocalTime.now().toString());          // Current time
        newAnnouncement.setOwnerId(ownerId);
        newAnnouncement.setModuleId(moduleId);  // Link the announcement to the module
    
        // Save and return the announcement
        Announcement savedAnnouncement = announcementRepository.save(newAnnouncement);
        log.info(log_header + "Announcement created successfully: " + savedAnnouncement);
        return savedAnnouncement;
    }
    

   public boolean deleteAnnouncement(long moduleId, long announcementId) {
        log.info(log_header + "Deleting announcement with ID: {} from module: {}", announcementId, moduleId);
    
        // Fetch the announcement
        Optional<Announcement> announcementOptional = announcementRepository.findById(announcementId);
        if (announcementOptional.isEmpty()) {
            log.error(log_header + "Announcement with ID " + announcementId + " not found.");
            throw new IllegalArgumentException("Announcement not found");
        }
    
        Announcement announcement = announcementOptional.get();
    
        // Validate the module
        if (announcement.getModuleId() != moduleId) {
            log.error(log_header + "Announcement with ID {} does not belong to module ID {}", announcementId, moduleId);
            throw new IllegalArgumentException("Announcement does not belong to the specified module");
        }
    
        // Delete the announcement
        announcementRepository.delete(announcement);
        log.info(log_header + "Announcement deleted successfully.");
    
        return true;
    }
    
    public List<Announcement> getAllAnnouncementsByStudent(long studentId) {
        log.info("Fetching all announcements for user: " + studentId);
        
        // Find the modules the student is enrolled in
        Iterable<Module> modules = moduleRepository.findAllByStudentId(studentId);
    
        // Find the announcements for each module
        List<Announcement> announcements = new ArrayList<>();
        if (modules != null) {
            for (Module module : modules) {
                List<Announcement> moduleAnnouncements = announcementRepository.findAllByModuleId(module.getModuleId());
                if (moduleAnnouncements != null) {
                    announcements.addAll(moduleAnnouncements);
                }
            }
        }
    
        return announcements;
    }
    

    public Module leaveModule(long moduleId, long studentId) {
        log.info(log_header + "Student: " + studentId + " leaving module: " + moduleId);
        Optional<Module> moduleOptional = moduleRepository.findById(moduleId);
        if (moduleOptional.isEmpty()) {
            log.error(log_header + "Module with ID: '" + moduleId + "'' does not exist");
            return null;
        }
        Module module = moduleOptional.get();
        if (module.getStudentList().contains(studentId)) {
            module.getStudentList().remove(studentId);
            moduleRepository.save(module);
            return module;
        } else {
            log.error(log_header + "Student: " + studentId + " is not enrolled in module: " + moduleId);
            return null;
        }
    }
}
