package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.Queue;
import com.example.demo.service.LoadingService;

@Component
public class LoadingController {

    private final LoadingService service;

    @Autowired
    public LoadingController(LoadingService service) {
        this.service = service;
    }

    @RabbitListener(queuesToDeclare = @Queue(name = "target_queue"))
    public void listen(String message) {
        System.out.println(message);
        service.loadDataToDB(message);
    }
}