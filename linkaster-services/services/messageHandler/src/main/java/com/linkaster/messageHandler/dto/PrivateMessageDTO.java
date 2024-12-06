package com.linkaster.messageHandler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 *  Title: PrivateMessageDTO.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PrivateMessageDTO {
    private String message;
    private long privateChatId;
}
