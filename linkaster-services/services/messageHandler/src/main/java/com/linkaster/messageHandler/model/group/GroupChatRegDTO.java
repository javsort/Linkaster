package com.linkaster.messageHandler.model.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupChatRegDTO {
    private String moduleId;
    private String moduleName;
    private String ownerUserId;
}
