package com.example.demo.services;

import com.example.demo.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ScheduledPushMessages {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(fixedRate = 3000)
    public void sendMessage() {
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        simpMessagingTemplate.convertAndSend("/topic/messages",
                new MessageDto("send with (@Scheduled) " + time));
    }
}
