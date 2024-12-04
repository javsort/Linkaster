package com.linkaster.moduleManager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.service.annotation.PatchExchange;

import com.linkaster.moduleManager.dto.AnnouncementCreate;
import com.linkaster.moduleManager.dto.EventCreate;
import com.linkaster.moduleManager.dto.JoinModuleCreate;
import com.linkaster.moduleManager.dto.ModuleCreate;
import com.linkaster.moduleManager.model.Announcement;
import com.linkaster.moduleManager.model.Module;
import com.linkaster.moduleManager.model.EventModel;

import jakarta.servlet.http.HttpServletRequest;


@RequestMapping("/api/module")
public interface APIModuleController {

    @GetMapping("")
    public String home();

    @GetMapping("/status")
    public String status();

    @PostMapping("/create")
    public ResponseEntity<?> createModule(@RequestBody ModuleCreate module, HttpServletRequest request);

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteModule(@PathVariable long id);

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateModule(@PathVariable long id, @RequestBody ModuleCreate module, HttpServletRequest request);

    @GetMapping("/getAllModules")
    @ResponseStatus(HttpStatus.OK)
    public List<Module> getAllModules();


    @GetMapping("/moduleId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Module getModuleById(@PathVariable long id);


    @GetMapping("/students/{moduleId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Long> getStudentsByModule(@PathVariable long moduleId);
    

    @GetMapping("/students")
    @ResponseStatus(HttpStatus.OK)
    public List<Module> getModulesByStudent(HttpServletRequest request);
    
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public boolean joinModuleByCode(@RequestBody JoinModuleCreate joinModule, HttpServletRequest request);
    

    @PostMapping("/leave/{moduleId}")
    @ResponseStatus(HttpStatus.OK)
    public void leaveModule(@PathVariable long moduleId, HttpServletRequest request);


    @PostMapping("/announcement")
    public ResponseEntity<?> createAnnouncement(@RequestBody AnnouncementCreate announcement, HttpServletRequest request);

    @PostMapping("/announcement/{id}/{moduleId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteAnnouncement(@PathVariable long announcementId, @RequestBody long moduleId);


    @GetMapping("/announcement/user")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Announcement> getAllAnnouncementsByUserId(HttpServletRequest request);


    @PostMapping("/event")
    public ResponseEntity<?> createEvent(@RequestBody EventCreate event, HttpServletRequest request);

    @GetMapping("/events/user")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<EventModel> getAllEventsByUserId(HttpServletRequest request);

    @GetMapping("/events/{moduleId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllEventsByModuleId(@RequestBody long moduleId);

    /* 
    @GetMapping("/updateTimetable")
    @ResponseStatus(HttpStatus.OK)
    public boolean updateTimetable(@RequestParam Integer time, @RequestParam Date date);
    */
    
    // Called by the student service - INTERSERVICE COMMUNICATION
    @GetMapping("/student/teachers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Iterable<Long>> getTeachersByStudent(HttpServletRequest request);
}
