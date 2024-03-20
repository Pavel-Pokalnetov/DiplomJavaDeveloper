package ru.slenergo.AppMonitoring.model.EMailSender;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * Класс отправителя EMail
 */
@Component
@Getter
public class EmailService {

    private final String address;
    private final String port;
    private final String recipient;
    private final String sender = "appmon@slenergo.ru";

    public EmailService(@Value("${mailserver.address}") String address,
                        @Value("${mailserver.port}") String port,
                        @Value("${recipient}") String recipient) {
        this.address = address;
        this.port = port;
        this.recipient = recipient;
    }

    /**
     * Отправка сообщения на почтовый ящик администратора
     *
     * @param emailModel
     * @return
     */
    public boolean sendMailToAdmin(EmailModel emailModel) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", address);
        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailModel.getEmail()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Отзыв из формы обратной связи сервиса мониторинга гидравлических режимов СПВК от "
                    + emailModel.getName());
            message.setText(emailModel.getMessage());
            Transport.send(message);
            System.out.println("Email Sent successfully....");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
