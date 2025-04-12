package com.microservices.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    @Bean
    public NewTopic orderServiceTopic() {
        return new NewTopic("order-service", 1, (short) 1);
    }
}
