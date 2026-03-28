package com.example.demo;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableRabbit
@EnableCaching
public class LoadingDataApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(LoadingDataApplication.class);
        // Use a random port to avoid clashes when multiple modules run concurrently
        Map<String, Object> defaults = new HashMap<>();
        defaults.put("server.port", "0");
        app.setDefaultProperties(defaults);
        app.run(args);
    }

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