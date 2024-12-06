package com.linkaster.timetableService.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * DTO to be received from module manager to sprout the upcoming events
 */

 
/*
 *  Title: EventSeedDTO.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EventSeedDTO {

    /*
     * Details for event to be sprouted
     */
    private Long moduleId;
    private String name;
    private String startTime;
    private String endTime;
    private Date startDate;
    private String room;
    private Long ownerId;

    /*
     * Arguments for the sprouting
     */
    // Repetitions to generate for the event
    private String repsToGen;

    // Interval between repetitions
    // [daily, weekly, monthly]
    private String interval;

    // To existing timetables to add
    private List<Long> userIdsInModule;

}
