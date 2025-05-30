package org.example;

import org.example.configuration.DataSourceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"org.example.controller", "org.example.service", "org.example.repository"})
@PropertySource("classpath:application.properties")
@Import({DataSourceConfiguration.class})
public class TestConfig {
}