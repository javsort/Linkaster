package com.linkaster.moduleManager.dto;

import java.util.List;

import com.linkaster.moduleManager.model.Announcement;

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
    private String type;
    private String moduleName;
    private String moduleCode;

}
