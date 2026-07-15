package com.tnf.message_consumer.Consumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.tnf.common_dto.dto.MessageDto;

@Component
public class RabbitMessageConsumer {
    @RabbitListener(queues = "message-queue")
    public void consume(MessageDto dto) {
        System.out.println("=======");
        System.out.println("RabbitMQ Message Recieved");
        System.out.println(dto);
        System.out.println("=======");
    }
}
