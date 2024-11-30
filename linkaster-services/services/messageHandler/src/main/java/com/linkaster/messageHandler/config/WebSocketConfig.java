package com.linkaster.messageHandler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.linkaster.messageHandler.webSocket.WebSocketOverseer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSocket
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {

    @Value("${address.logicGateway.url}")
    private String logicGatewayUrl;

    private final String log_header = "WebSocketConfig --- ";

    // 'Tis I, the overseer of the web socket.
    private final WebSocketOverseer webSocketOverseer;

    @Autowired
    public WebSocketConfig(WebSocketOverseer webSocketOverseer) {
        this.webSocketOverseer = webSocketOverseer;
    }

    /*@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        log.info(log_header + "Registering Stomp Endpoints for registry: " + registry);

        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // Allow all origins -> To change with logicGatewayUrl
                .withSockJS();
                //.setAllowedOrigins("*")     // Allow all origins -> To change with logicGatewayUrl 
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registration) {
        registration.enableSimpleBroker("/topic");
        registration.setApplicationDestinationPrefixes("/app");
    }*/

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        log.info(log_header + "Registering WebSocket Handlers for registry: " + registry);

        registry.addHandler(webSocketOverseer, "/ws")
                .setAllowedOriginPatterns("*"); // Allow all origins -> To change with logicGatewayUrl
                //.withSockJS();
                //.setAllowedOrigins("*")     // Allow all origins -> To change with logicGatewayUrl 
    }

}
