package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // префикс на которые клиенты подрисываются для получения сообщений
        registry.enableSimpleBroker("/topic", "/user");
        // префмкс на который клиенты шлют сообщения
        registry.setApplicationDestinationPrefixes("/app");
        //  префикс для отправки конкретному клиенту сообщения
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //конечная точка, с которой будет осуществляться соединение
        registry.addEndpoint("/stomp")
//                .setAllowedOrigins("http://localhost:63343")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
