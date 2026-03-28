package com.kafka.transform;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableRabbit
public class TransformingData {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TransformingData.class);
        // Use a random port to avoid clashes when multiple modules run concurrently
        Map<String, Object> defaults = new HashMap<>();
        defaults.put("server.port", "0");
        app.setDefaultProperties(defaults);
        app.run(args);
    }

    @RabbitListener(queuesToDeclare = @Queue(name = "source_queue"))
    public void handleMessage(String value) {
        int res = 0;
        String[] fileData = value.split(",");
        String operator = fileData[2];

        switch (operator) {
            case "+":
                res = Integer.parseInt(fileData[0]) + Integer.parseInt(fileData[1]);
                break;
            case "-":
                res = Integer.parseInt(fileData[0]) - Integer.parseInt(fileData[1]);
                break;
            case "*":
                res = Integer.parseInt(fileData[0]) * Integer.parseInt(fileData[1]);
                break;
            case "/":
                res = Integer.parseInt(fileData[0]) / Integer.parseInt(fileData[1]);
                break;
            default:
                // Unsupported operator, keep result as 0
                break;
        }

        String result = value + "," + res;
        rabbitTemplate.convertAndSend("target_queue", result);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    // Provide a DataSource bean for PostgreSQL to satisfy auto‑configuration
    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        ds.setUsername("postgres");
        ds.setPassword("postgres");
        return ds;
    }
}