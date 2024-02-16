package ru.slenergo.AppMonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AppMonitoring {
    public static void main(String[] args) {
        try {
            SpringApplication.run(AppMonitoring.class, args);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
