package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.sql.SQLException;

@SpringBootApplication
@EnableCircuitBreaker
@EnableEurekaClient
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class BookApplication {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(BookApplication.class, args);

//        Console.main(args);
    }
}