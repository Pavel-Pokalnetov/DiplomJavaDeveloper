package ru.slenergo.AppMonitoring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.slenergo.AppMonitoring.services.UserServices;

@RestController
public class TestController {
    @Autowired
    UserServices us;
    @RequestMapping("/test")
    public String testRequest(){
        return us.getUsers().toString();
    }
}