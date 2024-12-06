package com.linkaster.messageHandler.model.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 *  Title: GroupChatRegDTO.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupChatRegDTO {
    private String moduleId;
    private String moduleName;
    private String ownerUserId;
}
