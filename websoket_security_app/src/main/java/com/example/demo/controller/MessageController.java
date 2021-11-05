package com.example.demo.controller;

import com.example.demo.model.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/sendAll")
//    @SendTo("/myTopic/messages")
    public MessageDto getMessages(SimpMessageHeaderAccessor sha, Principal user, @Payload MessageDto dto) {
        System.out.println(user.getName());
        System.out.println(sha.getSessionId());
        System.out.println(dto);

        simpMessagingTemplate.convertAndSend("/topic/messages", dto);
        return dto;
    }

    @MessageMapping("/sendMe")
    public void sendSpecific(@Payload MessageDto messageDto,
                             Principal user,
                             @Header("simpSessionId") String sessionId) throws Exception {

        System.out.println(user.getName());
        System.out.println(sessionId);
        System.out.println(messageDto);

        MessageDto out = new MessageDto(
                new SimpleDateFormat("HH:mm:ss").format(new Date()));
        simpMessagingTemplate.convertAndSendToUser(
                user.getName(), "/queue/messages", out);
    }
}