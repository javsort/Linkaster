package com.linkaster.messageHandler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.linkaster.messageHandler.webSocket.WebSocketOverseer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${address.logicGateway.url}")
    private String logicGatewayUrl;

    private final String log_header = "WebSocketConfig --- ";

    // 'Tis I, the overseer of the web socket.
    private final WebSocketOverseer webSocketOverseer;

    @Autowired
    public WebSocketConfig(WebSocketOverseer webSocketOverseer) {
        this.webSocketOverseer = webSocketOverseer;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        log.info(log_header + "Registering Stomp Endpoints for registry: " + registry);

        registry.addEndpoint("/ws")
                .addInterceptors(webSocketOverseer)
                .setAllowedOrigins("*");     // Allow all origins -> To change with logicGatewayUrl 
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registration) {
        registration.enableSimpleBroker("/topic");
        registration.setApplicationDestinationPrefixes("/app");
    }

}
