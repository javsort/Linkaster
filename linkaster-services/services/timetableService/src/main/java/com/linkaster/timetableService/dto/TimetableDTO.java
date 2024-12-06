package com.linkaster.timetableService.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


/*
 *  Title: TimetableDTO.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TimetableDTO {
    private Date date;
    private String name;
    private String startTime;
    private String endTime;
    private String room;
    private String ownerName;
    private boolean isMandatory;
    
}
