package com.linkaster.messageHandler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 *  Title: ActorMetadata.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorMetadata {
    private String publicKey;
    private long userId;
    private String name;
}
