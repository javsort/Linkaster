package com.linkaster.moduleManager.dto;

import com.linkaster.moduleManager.model.Module;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnnouncementCreate {
    private long id;
    private String name;
    private String message;
    private long owner_id;
    private String time;
    private Date date;
    private Module moduleId;

}