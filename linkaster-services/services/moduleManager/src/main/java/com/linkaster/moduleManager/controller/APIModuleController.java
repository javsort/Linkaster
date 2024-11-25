package com.linkaster.moduleManager.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.linkaster.moduleManager.dto.ModuleCreate;
import com.linkaster.moduleManager.model.EventModel;
import com.linkaster.moduleManager.model.Module;


@RequestMapping("/api/module")
public interface APIModuleController {

    @GetMapping("")
    public String home();

    @PostMapping("/createModule")
    @ResponseStatus(HttpStatus.CREATED)
    public Module createModule(@RequestBody ModuleCreate module);


    @PostMapping("/deleteModule/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteModule(@PathVariable long id);


    @PostMapping("/updateModule/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean updateModule(@PathVariable long id, @RequestBody ModuleCreate module);


    @GetMapping("/getAllModules")
    @ResponseStatus(HttpStatus.OK)
    public List<Module> getAllModules();


    @GetMapping("/getModuleById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Module getModuleById(@PathVariable long id);


    @PostMapping("/assignTeacher")
    @ResponseStatus(HttpStatus.OK)
    public boolean assignTeacher(@RequestParam long teacherUserId, @RequestParam long moduleId);


    @GetMapping("/getStudentsByModule/{moduleId}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getStudentsByModule(@PathVariable long moduleId);


    @GetMapping("/joinModulebyCode/{code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean joinModuleByCode(@PathVariable String code, @RequestParam long studentId);


    @GetMapping("/leaveModule/{moduleId}/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public void leaveModule(@PathVariable long moduleId, @PathVariable long studentId);


    @GetMapping("/newAssignment")
    @ResponseStatus(HttpStatus.OK)
    public String newAssignment();


    @GetMapping("/deleteAssignment")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAssignment();


    @GetMapping("/updateAssignment")
    @ResponseStatus(HttpStatus.OK)
    public boolean updateAssignment();


    @GetMapping("/getAllAssignments")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Iterable<EventModel>> getAllAssignments();


    @GetMapping("/newAnnouncement")
    @ResponseStatus(HttpStatus.OK)
    public String newAnnouncement();


    @GetMapping("/deleteAnnouncement")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteAnnouncement();


    @GetMapping("/updateAnnouncement")
    @ResponseStatus(HttpStatus.OK)
    public boolean updateAnnouncement();


    @GetMapping("/getAllAnnouncements")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Iterable<EventModel>> getAllAnnouncements();


    @GetMapping("/updateTimetable")
    @ResponseStatus(HttpStatus.OK)
    public boolean updateTimetable(@RequestParam Integer time, @RequestParam Date date);

    
    // Called by the student service - INTERSERVICE COMMUNICATION
    @GetMapping("/student/{studentId}/teachers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Iterable<Long>> getTeachersByStudent(@PathVariable String studentId);
}
