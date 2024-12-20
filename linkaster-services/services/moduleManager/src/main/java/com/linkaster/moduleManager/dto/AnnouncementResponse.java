/*
 *  Title: AnnouncementResponse.java
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */


package com.linkaster.moduleManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementResponse {
    private long id;            // Announcement ID
    private String message;     // Content of the announcement
    private long ownerId;       // Owner of the announcement (User ID)
    private String ownerName;   // Owner of the announcement (User name)
    private String time;        // Time of announcement creation
    private Date date;        // Date of announcement creation
    private long moduleId;      // ID of the module to which the announcement belongs
}
