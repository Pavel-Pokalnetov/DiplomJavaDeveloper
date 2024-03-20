package ru.slenergo.AppMonitoring.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.slenergo.AppMonitoring.model.EMailSender.EmailModel;
import ru.slenergo.AppMonitoring.model.EMailSender.EmailService;

@Controller
public class FeedbackController {
    final EmailService emailService;

    public FeedbackController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Отправка сообщения обратной связи.
     *
     * @param emailModel
     * @param model
     * @return
     */
    @PostMapping("/feedback")
    public String feedback(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String subject,
                           @RequestParam(required = false) String message,
                           Model model) {
        model.addAttribute("url", "/");
        if ((name == null) || (email == null) || (subject == null) || (message == null)) {
            model.addAttribute("result", "Ошибка в запросе");
        } else {

            if (emailService.sendMailToAdmin(new EmailModel(name, email, subject, message))) {
                model.addAttribute("result", "Ошибка в запросе.");
            }
            else{
                model.addAttribute("result", "Сообщение не отправлено.");
            }
        }
        return "result";
    }
}
