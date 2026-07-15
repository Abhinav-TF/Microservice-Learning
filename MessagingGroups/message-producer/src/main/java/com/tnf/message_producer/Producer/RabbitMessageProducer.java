package com.tnf.message_producer.Producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.tnf.common_dto.dto.MessageDto;
import com.tnf.message_producer.Config.RabbitMQConfig;

@Component
public class RabbitMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(MessageDto dto){
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE, dto);
    }
}
