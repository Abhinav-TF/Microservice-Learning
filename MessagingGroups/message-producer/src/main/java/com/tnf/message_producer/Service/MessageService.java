package com.tnf.message_producer.Service;

import org.springframework.stereotype.Service;

import com.tnf.common_dto.dto.MessageDto;
import com.tnf.message_producer.Producer.KafkaProducer;
import com.tnf.message_producer.Producer.RabbitMessageProducer;

@Service
public class MessageService {
    private final KafkaProducer producer;
    private final RabbitMessageProducer rabbitProducer;

    public MessageService(KafkaProducer producer, RabbitMessageProducer rabbitProducer) {
        this.producer = producer;
        this.rabbitProducer = rabbitProducer;
    }

    public void send(MessageDto message) {
        producer.sendMessage(message);
        rabbitProducer.send(message);
    }
}
