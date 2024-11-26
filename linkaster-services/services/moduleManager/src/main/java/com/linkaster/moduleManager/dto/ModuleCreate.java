package com.linkaster.moduleManager.dto;

import java.util.List;

import com.linkaster.moduleManager.model.Announcement;
import com.linkaster.moduleManager.model.EventModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ModuleCreate {
    private String name;
    private String moduleCode;
    private String type;
    private List<Long> students;
    private List<EventModel> events;
    private List<Announcement> announcements;
    private List<String> studentIds;

    // ClubModule only fields
    private String clubLeaderStudentId;
    private String clubLeader;

    // ClassModule only fields
    private String teacherId;
    private String teacherName;

}
