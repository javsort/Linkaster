package com.linkaster.logicGateway.dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class Module {

    private Long id;
    private Long moduleOwnerId;
    private String moduleOwnerName;
    private String moduleOwnerType;
    private String moduleName;
    private String moduleCode;   
    private List<Long> studentList;
    private String type;
    
}
