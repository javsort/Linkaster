package com.linkaster.moduleManager.dto;

import com.linkaster.moduleManager.model.Module;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class EventCreate {
    private long id;
    private String name;
    private String startTime;
    private String endTime;
    private String room;
    private Date date;
    private long moduleId;
}