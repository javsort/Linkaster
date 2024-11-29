package com.linkaster.messageHandler.model.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupChatReg {
    private long moduleId;
    private String moduleName;
    private long ownerUserId;
}
