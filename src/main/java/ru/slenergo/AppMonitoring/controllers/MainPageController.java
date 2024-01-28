package ru.slenergo.AppMonitoring.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping({"/"})
public class MainPageController {

    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
