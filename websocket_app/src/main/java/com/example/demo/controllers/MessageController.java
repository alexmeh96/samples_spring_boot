package com.example.demo.controllers;

import com.example.demo.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // клиент отправляет сообщение на /app/sendAll
    @MessageMapping("/sendAll")
    // отправить клиентам сообщение, подписанным на /topic/messages
    @SendTo("/topic/messages")
    public MessageDto getMessages(@Payload MessageDto messageDto) {

        MessageDto out = new MessageDto(messageDto.getMessage() + " - " +
                new SimpleDateFormat("HH:mm:ss").format(new Date()));

        return out;
    }

    @MessageMapping("/sendMe")
    public void sendSpecific(
            @Payload MessageDto messageDto,
            @Header("simpSessionId") String sessionId) {

        System.out.println(sessionId);

        MessageDto out = new MessageDto(messageDto.getMessage() + " - " +
                new SimpleDateFormat("HH:mm:ss").format(new Date()));


        // отправка клиенту происходит который подписан на /user/${sessionId}/queue/messages
        simpMessagingTemplate.convertAndSendToUser(
                sessionId, "/queue/messages", out);
    }
}