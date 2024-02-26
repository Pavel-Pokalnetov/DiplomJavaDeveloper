package ru.slenergo.AppMonitoring.configuration;

import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class Config {
    public static final DateTimeFormatter formatterTimeOnly = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter formatterDateOnly = DateTimeFormatter.ofPattern("dd.MM.uuuu");
    public static final DateTimeFormatter formatterDateTimeFull = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm");
}
