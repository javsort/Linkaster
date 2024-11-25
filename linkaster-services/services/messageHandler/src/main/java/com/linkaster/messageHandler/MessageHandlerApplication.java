package com.linkaster.messageHandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MessageHandlerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageHandlerApplication.class, args);
    }
}
