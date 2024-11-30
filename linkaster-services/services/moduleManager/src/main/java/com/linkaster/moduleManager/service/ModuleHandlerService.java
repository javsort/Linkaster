package com.linkaster.moduleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.model.Module;
import com.linkaster.moduleManager.model.Announcement;
import com.linkaster.moduleManager.repository.ModuleRepository;
import com.linkaster.moduleManager.repository.AnnouncementRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    public Announcement createAnnouncement(long moduleId, String announcement, long ownerId) {
        log.info(log_header + "Creating announcement for module: " + moduleId);

        // Validate module existence
        Optional<Module> moduleOptional = moduleRepository.findById(moduleId);
        if (moduleOptional.isEmpty()) {
            log.error(log_header + "Module with ID " + moduleId + " not found.");
            throw new IllegalArgumentException("Module not found");
        }

        // Create the announcement
        Module module = moduleOptional.get();
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setModule(module);
        newAnnouncement.setMessage(announcement);
        newAnnouncement.setDate(new java.sql.Date(System.currentTimeMillis())); // Current date
        newAnnouncement.setTime(java.time.LocalTime.now().toString()); // Current time
        newAnnouncement.setOwnerId(ownerId);

        // Save and return
        Announcement savedAnnouncement = announcementRepository.save(newAnnouncement);
        log.info(log_header + "Announcement created successfully: " + savedAnnouncement);
        return savedAnnouncement;
    }


    public Announcement deleteAnnouncement(long moduleId, long announcementId) {
        log.info(log_header + "Deleting announcement - " + announcementId + " - from module: " + moduleId);

        // Fetch the announcement
        Optional<Announcement> announcementOptional = announcementRepository.findById(announcementId);
        if (announcementOptional.isEmpty()) {
            log.error(log_header + "Announcement with ID " + announcementId + " not found.");
            throw new IllegalArgumentException("Announcement not found");
        }

        Announcement announcement = announcementOptional.get();

        // Validate the module
        if (announcement.getModule().getId() != moduleId) {
            log.error(log_header + "Announcement does not belong to module ID " + moduleId);
            throw new IllegalArgumentException("Announcement does not belong to the specified module");
        }

        // Delete the announcement
        announcementRepository.delete(announcement);
        log.info(log_header + "Announcement deleted successfully: " + announcement);
        return announcement;
    }

    public Iterable<Announcement> getAllAnnouncements() {
        log.info(log_header + "Fetching all announcements");
        return announcementRepository.findAll(); // Return the result from the repository
    }
    
    public List<Announcement> getAllAnnouncementsByStudent(long studentId) {
        log.info("Fetching all announcements for user: " + studentId);
        
        // Find the modules the student is enrolled in
        Iterable<Module> modules = moduleRepository.findAllByStudentId(studentId);
    
        // Find the announcements for each module
        List<Announcement> announcements = new ArrayList<>();
        if (modules != null) {
            for (Module module : modules) {
                List<Announcement> moduleAnnouncements = announcementRepository.findAllByModuleId(module.getId());
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
