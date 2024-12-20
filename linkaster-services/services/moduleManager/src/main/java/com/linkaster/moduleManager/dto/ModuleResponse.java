/*
 *  Title: ModuleRespose.java
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */


package com.linkaster.moduleManager.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleResponse {
    private Long moduleId;
    private String moduleName;
    private String moduleCode;
    private String moduleOwnerName;
    private String moduleOwnerType;
    private Long moduleOwnerId;
    private List<Long> studentList;
    private String type;
}