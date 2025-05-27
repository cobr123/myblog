package org.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"org.example.controller", "org.example.service", "org.example.repository"})
@PropertySource("classpath:application.properties")
public class TestConfig {
}