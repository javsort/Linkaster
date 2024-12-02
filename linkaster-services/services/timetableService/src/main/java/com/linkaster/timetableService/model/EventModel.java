package com.linkaster.timetableService.model;


import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "upcoming_events")
public class EventModel {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "event_module_id", nullable = false)  
    private Long moduleId;

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
    private Long ownerId;

    @ManyToOne
    @JoinColumn(name = "timetable_id", nullable = false)
    private Timetable timetable;

}
