package ru.slenergo.AppMonitoring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImputDataController {
    @GetMapping("/input/vos5")
    public String inputDataVos5(){
        return "inputdataVos5";
    }

    @GetMapping("/input/vos15")
    public String inputDataVos15(){
        return "inputdataVos15";
    }




}
