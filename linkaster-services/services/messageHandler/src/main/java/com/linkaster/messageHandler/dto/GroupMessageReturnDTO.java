package com.linkaster.messageHandler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupMessageReturnDTO {
    private String message;
    private long groupChatId;
    private long senderId;
    private String senderName;
    private Date timestamp;
       
}
