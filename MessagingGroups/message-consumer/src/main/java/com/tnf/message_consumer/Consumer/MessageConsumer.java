package com.tnf.message_consumer.Consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.tnf.common_dto.dto.MessageDto;

@Component
public class MessageConsumer {
    
    @KafkaListener(topics = "message-topic", groupId = "message-group")
    public void consume(MessageDto dto) {
        System.out.println("=======");
        System.out.println("Message Recieved");
        System.out.println(dto);
        System.out.println("=======");
    }
}
