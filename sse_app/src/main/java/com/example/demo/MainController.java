package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private ExecutorService executor
            = Executors.newCachedThreadPool();

    @GetMapping("/time")
    @CrossOrigin
    public SseEmitter streamDateTime() {

        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        sseEmitter.onCompletion(() -> LOGGER.info("SseEmitter is completed"));
        sseEmitter.onTimeout(() -> LOGGER.info("SseEmitter is timed out"));
        sseEmitter.onError((ex) -> LOGGER.info("SseEmitter got error:", ex));

        executor.execute(() -> {
            for (int i = 0; i < 15; i++) {
                try {
                    //отправка в стандартный приёмник на клиенте
                    sseEmitter.send(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));

                    Thread.sleep(1000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    sseEmitter.completeWithError(e);
                }
            }
            sseEmitter.complete();
        });

        executor.execute(() -> {
            for (int i = 0; i < 5; i++) {

                Message message = new Message("Custom message",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));

                try {
                    //отправка в приёмник на клиенте под названием "Custom"
                    sseEmitter.send(SseEmitter.event().name("Custom").data(message, MediaType.APPLICATION_JSON));

                    Thread.sleep(2000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    sseEmitter.completeWithError(e);
                }
            }
//            sseEmitter.complete();
        });

        LOGGER.info("Controller exits");
        return sseEmitter;
    }
}
