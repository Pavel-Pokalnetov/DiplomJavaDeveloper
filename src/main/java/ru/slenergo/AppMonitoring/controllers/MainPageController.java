package ru.slenergo.AppMonitoring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.slenergo.AppMonitoring.model.DataVos5;
import ru.slenergo.AppMonitoring.services.DataService;
import ru.slenergo.AppMonitoring.services.UserServices;

import java.util.List;

@Controller
public class MainPageController {
    @Autowired
    DataService dataService;
    @Autowired
    UserServices userServices;

    @GetMapping("/main/vos5")
    public String mainPageVos5(Model model) {
        List<DataVos5> dataVos5 = dataService.getAllVos5();
        model.addAttribute("dataVos5", dataVos5);
        return "mainVos5";
    }

//    @GetMapping("/main/vos15")
//    public String mainPageVos15(Model model){
//        List<DataVos15> data = dataService.getAllVos15();
//        model.addAttribute("dataVos",data);
//        return "mainVos15";
//    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
