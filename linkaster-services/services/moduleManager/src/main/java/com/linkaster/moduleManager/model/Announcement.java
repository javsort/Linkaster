/*
 *  Title: Announcement.java
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */


package com.linkaster.moduleManager.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table (name = "announcements")
public class Announcement {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "announcement_module_id", nullable = false)
    private long moduleId;

    @Column(name = "announcement_message")
    private String message;

    @Column(name = "announcement_name")
    private String name;

    @Column(name = "announcement_date")
    private Date date;

    @Column(name = "announcement_time")
    private String time;

    @Column(name = "announcement_owner_id")
    private Long ownerId;

    @Column(name = "announcement_owner_name")
    private String ownerName;
    
}
