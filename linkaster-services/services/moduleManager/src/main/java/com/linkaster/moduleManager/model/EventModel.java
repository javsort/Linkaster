package com.linkaster.moduleManager.model;


import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@Table (name = "events")
public class EventModel {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "event_module_id", nullable = false)  
    private long moduleId;

    @Column(name = "event_name")
    private String name;

    @Column(name = "event_start_time")
    private String startTime;

    @Column(name = "event_end_time")
    private String endTime;

    @Column(name = "event_date")
    private Date date;

    @Column(name = "event_location")
    private String room;

    @Column(name = "event_owner_id")
    private long ownerId;

}
