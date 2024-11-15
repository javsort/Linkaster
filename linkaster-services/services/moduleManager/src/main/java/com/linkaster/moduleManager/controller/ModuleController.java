package com.linkaster.moduleManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

import com.linkaster.moduleManager.model.Module;
import com.linkaster.moduleManager.service.AuditManagerService;
import com.linkaster.moduleManager.service.JoinCodeManagerService;
import com.linkaster.moduleManager.service.ModuleHandlerService;
import com.linkaster.moduleManager.service.ModuleManagerService;
import com.linkaster.moduleManager.service.TimetableIntegratorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/module")
@Slf4j
public class ModuleController {

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

    @ResponseStatus(HttpStatus.OK)
    public String home() {
        return "Welcome to the Module Service!";
    }

    @ResponseStatus(HttpStatus.OK)
    public String status() {
        return "Module Service is up and running!";
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Module createModule(@RequestBody Module module) {
        return moduleManagerService.createModule(module);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteModule(@PathVariable long id) {
        moduleManagerService.deleteModule(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Module updateModule(@PathVariable long id, @RequestBody Module module) {
        return moduleManagerService.updateModule(id, module);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Module> getAllModules() {
        return moduleManagerService.getAllModules();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Module getModuleById(@PathVariable long id) {
        return moduleManagerService.getModuleById(id);
    }

    @PostMapping("/assignTeacher/{id}/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public Module assignTeacher(@PathVariable long id, @PathVariable long teacherId) {
        return moduleHandlerService.assignTeacher(id, teacherId);
    }




  

    @GetMapping("/studentsByModule/{moduleId}")
    @ResponseStatus(HttpStatus.OK)
    public String[] getStudentsByModule(@PathVariable String moduleId) {
        // Example logic; replace with actual implementation
        return new String[]{"student1", "student2", "student3"};
    }


    @PostMapping("/leaveModule/{moduleId}/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public void leaveModule(@PathVariable long moduleId, @PathVariable long studentId) {
        // Logic for a student to leave a module
    }

    @PostMapping("/updateTimetable")
    @ResponseStatus(HttpStatus.OK)
    public String updateTimetable(@RequestParam Integer time, @RequestParam Date date) {
        // Logic to update the timetable
        return "Timetable updated successfully";
    }
}
