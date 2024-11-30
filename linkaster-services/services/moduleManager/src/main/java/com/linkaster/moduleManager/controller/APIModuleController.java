package com.linkaster.moduleManager.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.service.annotation.PatchExchange;

import com.linkaster.moduleManager.dto.ModuleCreate;
import com.linkaster.moduleManager.model.Announcement;
import com.linkaster.moduleManager.model.EventModel;
import com.linkaster.moduleManager.model.Module;


@RequestMapping("/api/module")
public interface APIModuleController {

    @GetMapping("")
    public String home();

    @GetMapping("/status")
    public String status();

    @PostMapping("/createModule")
    @ResponseStatus(HttpStatus.CREATED)
    public Module createModule(@RequestBody ModuleCreate module);


    @DeleteMapping("/deleteModule/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteModule(@RequestBody long id);


    @PostMapping("/updateModule/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean updateModule(@RequestBody long id, @RequestBody ModuleCreate module);


    @GetMapping("/modules")
    @ResponseStatus(HttpStatus.OK)
    public List<Module> getAllModules();


    @GetMapping("/module/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Module getModuleById(@RequestBody long id);


    @GetMapping("/students/{moduleId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Long> getStudentsByModule(@RequestBody long moduleId);
    
    @GetMapping("/module/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Module> getModulesByStudent(@PathVariable long studentId);
    
    @PostMapping("/module/join")
    @ResponseStatus(HttpStatus.OK)
    public boolean joinModuleByCode(@RequestBody String code, @RequestBody long studentId);
    

    @PostMapping("/leaveModule")
    @ResponseStatus(HttpStatus.OK)
    public void leaveModule(@RequestBody long moduleId, @RequestBody long studentId);


    @PostMapping("/announcement")
    @ResponseStatus(HttpStatus.CREATED)
    public String createAnnouncement(@RequestBody long moduleId, @RequestBody long userId, @RequestBody String announcement);


    @DeleteMapping("/announcement/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteAnnouncement(@RequestBody long announcementId, @RequestBody long moduleId);


    @GetMapping("/announcement/{moduleId}")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Announcement> getAllAnnouncementsByModuleId(@RequestBody long moduleId);

    @GetMapping("/announcement/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Announcement> getAllAnnouncementsByUserId(@RequestBody long userId);
    /* 
    @GetMapping("/updateTimetable")
    @ResponseStatus(HttpStatus.OK)
    public boolean updateTimetable(@RequestParam Integer time, @RequestParam Date date);
    */
    
    // Called by the student service - INTERSERVICE COMMUNICATION
    @GetMapping("/student/{studentId}/teachers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Iterable<Long>> getTeachersByStudent(@RequestBody Long studentId);
}
