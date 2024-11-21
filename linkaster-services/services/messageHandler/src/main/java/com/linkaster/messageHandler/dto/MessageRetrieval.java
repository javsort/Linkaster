package com.linkaster.messageHandler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MessageRetrieval {
    private String requesterUserId;
    private String senderUserId;
}
