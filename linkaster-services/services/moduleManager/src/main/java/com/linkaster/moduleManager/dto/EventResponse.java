/*
 *  Title: EventResponse.java
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */


package com.linkaster.moduleManager.dto;

import com.linkaster.moduleManager.model.Module;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private long id;
    private String name;
    private String startTime;
    private String endTime;
    private String room;
    private Date date;
    private Module moduleId;

}