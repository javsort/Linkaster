package com.linkaster.moduleManager.dto;

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
    private String description;
    private String startTime;
    private String endTime;

    // ClubModule only fields
    private String clubLeaderStudentId;
    private String clubLeader;

    // ClassModule only fields
    private String teacherId;
    private String teacherName;

}
