package com.linkaster.messageHandler.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DecryptedMessage {
    private String decryptedMssg;
    private String senderId;
    private String receiverId;

    private Date date_sent;
}
