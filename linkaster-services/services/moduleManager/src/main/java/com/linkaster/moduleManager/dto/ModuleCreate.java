package com.linkaster.moduleManager.dto;

import java.util.List;

import com.linkaster.moduleManager.model.Announcement;
import com.linkaster.moduleManager.model.EventModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModuleCreate {
    private String name;
    private String moduleCode;
    private String type;
    private List<Long> studentsId;
    public List<Long> teachersId;

    // ClubModule only fields
    private String clubLeaderStudentId;
    private String clubLeader;

    // ClassModule only fields
    private String teacherId;
    private String teacherName;

}
