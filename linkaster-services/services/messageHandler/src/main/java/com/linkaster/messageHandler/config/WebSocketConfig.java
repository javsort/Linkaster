package com.linkaster.messageHandler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.linkaster.messageHandler.webSocket.WebSocketOverseer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${address.logicGateway.url}")
    private String logicGatewayUrl;

    // 'Tis I, the overseer of the web socket.
    private final WebSocketOverseer webSocketOverseer;

    @Autowired
    public WebSocketConfig(WebSocketOverseer webSocketOverseer) {
        this.webSocketOverseer = webSocketOverseer;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(webSocketOverseer)
                .setAllowedOrigins("*")     // Allow all origins -> To change with logicGatewayUrl  
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registration) {
        registration.enableSimpleBroker("/topic");
        registration.setApplicationDestinationPrefixes("/app");
    }

}
