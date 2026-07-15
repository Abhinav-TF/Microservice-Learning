package com.tnf.message_producer.Service;

import org.springframework.stereotype.Service;

import com.tnf.common_dto.dto.MessageDto;
import com.tnf.message_producer.Producer.KafkaProducer;

@Service
public class MessageService {
    private final KafkaProducer producer;

    public MessageService(KafkaProducer producer) {
        this.producer = producer;
    }

    public void send(MessageDto message) {
        producer.sendMessage(message);
    }
}
