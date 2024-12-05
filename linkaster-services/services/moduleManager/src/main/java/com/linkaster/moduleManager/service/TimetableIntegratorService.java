package com.linkaster.moduleManager.service;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TimetableIntegratorService {


    private final String log_header = "TimetableIntegratorService --- ";

/* 
    @Autowired
    public void updateTimetable(EventModel event) {
        log.info(log_header + "Updating timetable for event: " + event);

        // Step 1: Validate the event details
        if (event == null || event.getName() == null || event.getDate() == null || 
            event.getStartTime() == null || event.getRoom() == null) {
            log.warn(log_header + "Invalid event details: " + event);
            return; // Exit if event details are incomplete
        }

        // Step 2: Prepare the data payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("moduleName", event.getModule().getName()); // Assuming Module has a `getName()` method
        payload.put("eventName", event.getName());
        payload.put("type", event.getType());
        payload.put("date", event.getDate().toString()); // Convert Date to String
        payload.put("startTime", event.getStartTime());
        payload.put("endTime", event.getEndTime());
        payload.put("room", event.getRoom());

        try {
            // Step 3: Send the data to the timetable service
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);
            String timetableServiceUrl = "http://localhost:8080/api/timetable"; // Replace with your timetable service URL

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(timetableServiceUrl, requestEntity, String.class);

            // Step 4: Check the response
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info(log_header + "Timetable updated successfully for event: " + event);
            } else {
                log.error(log_header + "Failed to update timetable. Response: " + response.getBody());
            }
        } catch (Exception e) {
            // Handle any errors during the HTTP call
            log.error(log_header + "Error occurred while updating timetable: " + e.getMessage(), e);
        }
    }
*/

    
}
