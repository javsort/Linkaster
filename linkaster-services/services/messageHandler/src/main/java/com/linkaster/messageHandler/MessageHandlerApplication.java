package com.linkaster.messageHandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
 *  Title: MessageHandlerApplication.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@SpringBootApplication
@EnableScheduling
public class MessageHandlerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageHandlerApplication.class, args);
    }
}
