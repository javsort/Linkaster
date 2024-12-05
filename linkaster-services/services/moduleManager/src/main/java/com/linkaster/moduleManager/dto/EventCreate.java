package com.linkaster.moduleManager.dto;

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
    private String name;
    private String startTime;
    private String endTime;
    private String room;
    private Date date;
    private String moduleId;
}