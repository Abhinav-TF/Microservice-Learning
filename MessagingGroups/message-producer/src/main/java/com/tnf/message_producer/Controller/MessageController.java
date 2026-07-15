package com.tnf.message_producer.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tnf.common_dto.dto.MessageDto;
import com.tnf.message_producer.Service.MessageService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@Valid @RequestBody MessageDto dto) {
        //TODO: process POST request
        service.send(dto);
        return ResponseEntity.ok("Message sent successfully");
    }
    

}
