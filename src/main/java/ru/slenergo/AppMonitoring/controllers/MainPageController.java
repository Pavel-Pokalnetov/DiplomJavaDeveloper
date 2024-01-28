package ru.slenergo.AppMonitoring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.slenergo.AppMonitoring.services.UserServices;

@Controller
@RequestMapping({"/"})
public class MainPageController {
    @Autowired
    UserServices userServices;
    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
