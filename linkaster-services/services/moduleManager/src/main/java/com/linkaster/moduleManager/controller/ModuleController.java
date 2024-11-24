package com.linkaster.moduleManager.controller;

import org.hibernate.sql.ast.tree.update.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

import com.linkaster.moduleManager.dto.ModuleCreate;
import com.linkaster.moduleManager.dto.TeacherDTO;
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
    public List<String> getStudentsByModule(long moduleId) {
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
    }

    @Override
    public String newAssignment() {
        // Logic to create a new assignment
        return "New assignment created successfully";
    }

    @Override
    public void deleteAssignment() {
        // Logic to delete an assignment
    }

    @Override
    public boolean updateAssignment() {
        // Logic to update an assignment
        return true;
    }

    @Override
    public ResponseEntity<Iterable<EventModel>> getAllAssignments() {
        // Logic to get all assignments
        return null;
    }

    @Override
    public String newAnnouncement() {
        // Logic to create a new announcement
        return "New announcement created successfully";
    }

    @Override
    public boolean deleteAnnouncement() {
        // Logic to delete an announcement
        return true;
    }

    @Override
    public boolean updateAnnouncement() {
        // Logic to update an announcement
        return true;
    }

    @Override
    public ResponseEntity<Iterable<EventModel>> getAllAnnouncements() {
        // Logic to get all announcements
        return null;
    }

    @Override
    public boolean updateTimetable(Integer time, Date date) {
        // Logic to update the timetable
        log.info("Timetable updated successfully");
        return true;
    }

    @Override
    public ResponseEntity<Iterable<Long>> getTeachersByStudent(String studentId) {
        log.info(log_header + "Getting teachers for student: {}", studentId);
        return auditManagerService.getTeachersByStudent(studentId);
    }

}
