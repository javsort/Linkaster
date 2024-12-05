package com.linkaster.timetableService.control;
/*
 * This interface defines the API endpoints for the Timetable Controller.
 */

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linkaster.timetableService.dto.EventSeedDTO;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/timetable")
public interface  APITimetableController {

    // Home endpoint\
    @GetMapping("")
    public String home();
    
    // CreateTimetable endpoint -> called from user service after user creation
    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createTimetable(@PathVariable("userId") long userId);

    // DeleteTimetable endpoint -> called from user service after user deletion
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteTimetable(@PathVariable("userId") long userId);

    // Called by the Gateway to update a timetable from moduleManager
    @PostMapping("/sproutEvents")
    public ResponseEntity<?> sproutEvents(HttpServletRequest request, @RequestBody EventSeedDTO eventSeed);

    // Called by the Gateway to update a timetable from moduleManager
    @GetMapping("/getEvents")
    public ResponseEntity<?> getTimetableByUserId(HttpServletRequest request);
    
}
