package ru.slenergo.AppMonitoring.configuration;

import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class Config {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

}
