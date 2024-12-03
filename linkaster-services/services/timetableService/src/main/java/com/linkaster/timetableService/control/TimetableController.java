package com.linkaster.timetableService.control;

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
    public ResponseEntity<?> sproutEvents(HttpServletRequest request, @RequestBody EventSeedDTO eventSeedDTO){

        log.info(log_header + "Sprouting events from a moduleManager call...");


        log.info(log_header + "Received ping from moduleManager to sprout events for module with id: '" + eventSeedDTO.getModuleId() + "'.");
        eventSchedulingService.sproutEvents(eventSeedDTO);

        log.info(log_header + "Received ping from moduleManager to sprout events for module with id: '" + eventSeedDTO.getModuleId() + "'.");
        return ResponseEntity.ok("Call to Sprout Events for module with id: '" + eventSeedDTO.getModuleId() + "' sprouted successfully. Check logs for details and any unsuccessful calls.");
    }


}
