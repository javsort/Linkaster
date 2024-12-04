package com.linkaster.messageHandler.dto;

import java.sql.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupChatDTO {
    private long groupChatId;
    private long moduleId;

    private String moduleName;
    private Date lastMessageDate;

}
