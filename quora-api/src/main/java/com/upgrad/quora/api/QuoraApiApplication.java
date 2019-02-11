package com.upgrad.quora.api;

import com.upgrad.quora.service.ServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * A Configuration class that can declare one or more @Bean methods and trigger auto-configuration and component scanning.
 * This class launches a Spring Application from Java main method.
 */
@SpringBootApplication
@Import(ServiceConfiguration.class)
@Configuration
@EnableJpaRepositories(basePackages = {"com.upgrad.quora.service.dao"})
@ComponentScan("com.upgrad.quora.service")
@ComponentScan("com.upgrad.quora.api.controller")
@EntityScan("com.upgrad.quora.service.entity")
public class QuoraApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuoraApiApplication.class, args);
    }
}

