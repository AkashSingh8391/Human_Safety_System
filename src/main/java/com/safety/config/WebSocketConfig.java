package com.safety.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // broker destinations that clients can subscribe to
        config.enableSimpleBroker("/topic", "/queue"); 
        // prefix for messages bound for @MessageMapping methods
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // endpoint for websocket handshake (SockJS fallback)
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:5173") // frontend origin
                .withSockJS();
    }
}
