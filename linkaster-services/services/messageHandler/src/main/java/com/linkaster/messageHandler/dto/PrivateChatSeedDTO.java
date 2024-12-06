package com.linkaster.messageHandler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 *  Title: PrivateChatSeedDTO.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrivateChatSeedDTO {
    private String destEmail;
    private String requesterId;
}
