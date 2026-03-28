package com.example.demo;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
@EnableRabbit
@EnableCaching
public class LoadingDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoadingDataApplication.class, args);
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