package ru.slenergo.AppMonitoring.model.EMailSender;

import lombok.Data;

@Data
public class EmailModel {

    private String name;
    private String email;
    private String subject;
    private String message;

    public EmailModel(String name, String email, String subject, String message) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }
}
