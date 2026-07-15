package com.tnf.message_consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class MessageConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageConsumerApplication.class, args);
	}

}
