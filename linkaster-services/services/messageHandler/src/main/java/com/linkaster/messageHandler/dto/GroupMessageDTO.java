package com.linkaster.messageHandler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupMessageDTO {
    private String mssgToEncrypt;
    private String senderId;
    private String destinationModule;
}
