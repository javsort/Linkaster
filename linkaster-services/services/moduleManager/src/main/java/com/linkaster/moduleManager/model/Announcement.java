package com.linkaster.moduleManager.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Setter
@Getter
@Entity
@Table (name = "announcements")
public class Announcement {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "announcement_module_id", nullable = false)
    private Module module;

    @Column(name = "announcement_message")
    private String message;

    @Column(name = "announcement_date")
    private Date date;

    @Column(name = "announcement_time")
    private String time;

    @Column(name = "announcement_owner_id")
    private Long ownerId;
    
}
