package com.example.demo.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    // Queue used for publishing original records
    @Bean
    public Queue sourceQueue() {
        return new Queue("source_queue", true);
    }

    // Queue used for publishing transformed results
    @Bean
    public Queue targetQueue() {
        return new Queue("target_queue", true);
    }
}