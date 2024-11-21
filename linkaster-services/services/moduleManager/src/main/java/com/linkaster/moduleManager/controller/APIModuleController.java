package com.linkaster.moduleManager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.linkaster.moduleManager.model.Module;

public interface APIModuleController {

    @GetMapping("")
    public String home();

    @GetMapping("/createModule")
    public String createModule();

    @GetMapping("/deleteModule/{id}")
    public void deleteModule(@PathVariable long id);

    @GetMapping("/updateModule/{id}")
    public boolean updateModule(@PathVariable long id, @RequestBody Module module);

    @GetMapping("/getModuleById/{id}")
    public Module getModuleById(@PathVariable long id);

    @GetMapping("/getAllModules")
    public Iterable<Module> getAllModules();

    @GetMapping("/joinModulebyCode/{code}")
    public boolean joinModuleByCode(@PathVariable String code, @RequestParam long studentId);

    @GetMapping("/leaveModule/{moduleId}/{studentId}")
    public void leaveModule(@PathVariable long moduleId, @PathVariable long studentId);

    @GetMapping("/newAssignment")
    public String newAssignment();

    @GetMapping("/deleteAssignment")
    public void deleteAssignment();

    @GetMapping("/updateAssignment")
    public boolean updateAssignment();

    @GetMapping("/getAllAssignments")
    public Iterable<Module> getAllAssignments();

    @GetMapping("/newAnnouncement")
    public String newAnnouncement();

    @GetMapping("/deleteAnnouncement")
    public void deleteAnnouncement();

    @GetMapping("/updateAnnouncement")
    public boolean updateAnnouncement();

    @GetMapping("/getAllAnnouncements")
    public Iterable<Module> getAllAnnouncements();

    @GetMapping("/updateTimetable")
    public boolean updateTimetable();
}
