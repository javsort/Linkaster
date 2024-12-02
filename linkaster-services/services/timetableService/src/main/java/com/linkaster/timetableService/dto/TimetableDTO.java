package com.linkaster.timetableService.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


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
