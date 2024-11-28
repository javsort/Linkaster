package com.linkaster.moduleManager.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.moduleManager.dto.ModuleCreate;
import com.linkaster.moduleManager.model.Announcement;
import com.linkaster.moduleManager.model.EventModel;
import com.linkaster.moduleManager.model.Module;
import com.linkaster.moduleManager.service.AuditManagerService;
import com.linkaster.moduleManager.service.JoinCodeManagerService;
import com.linkaster.moduleManager.service.ModuleHandlerService;
import com.linkaster.moduleManager.service.ModuleManagerService;
import com.linkaster.moduleManager.service.TimetableIntegratorService;

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
    public Module createModule(ModuleCreate module) {
        return moduleManagerService.createModule(module);
    }

    @Override
    public boolean deleteModule(long id) {
        return moduleManagerService.deleteModule(id);
    }

    @Override
    public boolean updateModule(long id, ModuleCreate module) {
        return moduleManagerService.updateModule(id, module);
    }

    @Override
    public List<Module> getAllModules() {
        return moduleManagerService.getAllModules();
    }

    @Override
    public Module getModuleById(long id) {
        return moduleManagerService.getModuleById(id);
    }

    @Override
    public boolean assignTeacher(long moduleId, long teacherId) {
        return moduleHandlerService.assignTeacher(moduleId, teacherId);
    }

    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<Long> getStudentsByModule(long moduleId) {
        // Example logic; replace with actual implementation
        return auditManagerService.getStudentsByModule(moduleId);
    }

    @Override
    public boolean joinModuleByCode(String joinCode, long studentId) {
        return joinCodeManagerService.joinModuleByCode(joinCode, studentId);
    }

    @Override
    public void leaveModule(@PathVariable long moduleId, @PathVariable long studentId) {
        // Logic for a student to leave a module
        moduleHandlerService.leaveModule(moduleId, studentId);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public String createAnnouncement(@PathVariable long moduleId, @PathVariable long ownerId, @RequestBody String announcement) {
        // Logic to create a new announcement
        moduleHandlerService.createAnnouncement(moduleId, announcement, ownerId);
        return "New announcement created successfully";
    }


    @Override
    public boolean deleteAnnouncement(@PathVariable long announcementId, @PathVariable long moduleId) {
        // Logic to delete an announcement
        moduleHandlerService.deleteAnnouncement(announcementId, moduleId);
        return true;
    }


    @Override
    public Iterable<Announcement> getAllAnnouncementsByModuleId() {
        // Logic to get all announcements
        return moduleHandlerService.getAllAnnouncements();
    }

    /*
    @Override
    public boolean updateTimetable(Integer time, Date date) {
        return timetableIntegratorService.updateTimetable(time, date);
    }
    */

    // Called by the student service - INTERSERVICE COMMUNICATION
    @Override
    public ResponseEntity<Iterable<Long>> getTeachersByStudent(Long studentId) {
        log.info(log_header + "Getting teachers for student: {}", studentId);
        return auditManagerService.getTeachersByStudent(studentId);
    }

}
