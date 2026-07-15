package com.tnf.message_producer.Producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import com.tnf.common_dto.dto.MessageDto;

@Component
public class KafkaProducer {
    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
    @Value("${topic.name}")
    private String topicName;

    public KafkaProducer(KafkaTemplate<String, MessageDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MessageDto message) {
        kafkaTemplate.send(topicName, message);
    }

}
