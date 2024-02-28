package ru.slenergo.AppMonitoring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/history")
public class HistoryPageController {
    @GetMapping("/vos5")
    public String historyPageVos5(){
        return "history/historyVos5";
    }

    @GetMapping("/vos15")
    public String historyPageVos15(){
        return "redirect:/dev.html";
    }




}
