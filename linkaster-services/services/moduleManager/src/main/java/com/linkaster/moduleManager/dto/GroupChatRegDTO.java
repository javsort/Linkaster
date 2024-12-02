
package com.linkaster.moduleManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GroupChatRegDTO {
    private String moduleId;
    private String moduleName;
    private String ownerUserId;
}