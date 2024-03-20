package ru.slenergo.AppMonitoring.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.slenergo.AppMonitoring.model.EMailSender.EmailModel;
import ru.slenergo.AppMonitoring.model.EMailSender.EmailService;

@Controller
public class FeedbackController {
    final EmailService emailService;
    @Value("${recipient}") String recipient;

    public FeedbackController(EmailService emailService) {
        this.emailService = emailService;

    }

    /**
     * Обработка сообщения обратной связи.
     *
     * @param model
     * @return
     */
    @PostMapping("/feedback")
    public String feedback(EmailModel emailModel,
                           Model model) {
        model.addAttribute("url", "/");
        if (emailModel == null) {
            model.addAttribute("result", "Ошибка в запросе");
        } else {

            if (emailService.sendMailToAdmin(emailModel)){
                model.addAttribute("result", "Сообщение отправлено.");
            }
            else{
                model.addAttribute("result", "При отправке сообщения произошла ошибка." +
                        "Вы можете направить ваше сообщение по почте на адрес "+recipient);
            }
        }
        return "result";
    }
}
