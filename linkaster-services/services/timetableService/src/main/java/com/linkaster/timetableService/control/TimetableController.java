package com.linkaster.timetableService.control;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.timetableService.dto.EventSeedDTO;
import com.linkaster.timetableService.service.EventSchedulingService;
import com.linkaster.timetableService.service.TimetableCoordinatorService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/*
 *  Title: TimetableController.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Slf4j
@RestController
public class TimetableController implements APITimetableController {

    private final String log_header = "TimetableController --- ";

    private final EventSchedulingService eventSchedulingService;
    private final TimetableCoordinatorService timetableCoordinatorService;

    @Autowired
    public TimetableController(EventSchedulingService eventSchedulingService, TimetableCoordinatorService timetableCoordinatorService) {
        this.eventSchedulingService = eventSchedulingService;
        this.timetableCoordinatorService = timetableCoordinatorService;
    }

    /*
     * Forward to TimetableCoordinator
     */
    @Override
    public String home() {
        return "Welcome to the Timetable Service!";
    }

    // Create user's timetable
    @Override
    public ResponseEntity<?> createTimetable(@PathVariable("userId") long userId) {
        log.info(log_header + "Creating timetable...");
        return timetableCoordinatorService.createTimetable(userId);
    }

    // Delete user's timetable
    @Override
    public ResponseEntity<?> deleteTimetable(@PathVariable("userId") long userId) {
        return timetableCoordinatorService.deleteTimetable(userId);
    }

    @Override
    /*
     * Get user Id from request, the rest from seedDTO -> Forward to EventSchedulingService
     */
    public ResponseEntity<Boolean> sproutEvents(HttpServletRequest request, @RequestBody EventSeedDTO eventSeedDTO){

        log.info(log_header + "Sprouting events from a moduleManager call...");


        log.info(log_header + "Received ping from moduleManager to sprout events for module with id: '" + eventSeedDTO.getModuleId() + "'.");
        eventSchedulingService.sproutEvents(eventSeedDTO);

        log.info(log_header + "Received ping from moduleManager to sprout events for module with id: '" + eventSeedDTO.getModuleId() + "'.");
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<?> getTimetableByUserId(HttpServletRequest request) {
        String studentIdString = (String) request.getAttribute("id");
        long userId;

        try {
            userId = Long.parseLong(studentIdString);
        } catch (NumberFormatException e) {
            log.error(log_header + "Invalid student ID: " + studentIdString);
            return (ResponseEntity<?>) Collections.emptyList();
        }

        return ResponseEntity.ok(timetableCoordinatorService.getTimetableByUserId(userId));
    }


}
