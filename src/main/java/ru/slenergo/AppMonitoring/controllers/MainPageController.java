package ru.slenergo.AppMonitoring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.slenergo.AppMonitoring.model.entity.DataVos15;
import ru.slenergo.AppMonitoring.model.entity.DataVos5;
import ru.slenergo.AppMonitoring.model.services.DataServiceVOS15;
import ru.slenergo.AppMonitoring.model.services.DataServiceVOS5;
import ru.slenergo.AppMonitoring.model.services.UserServices;

import java.util.List;

@Controller
public class MainPageController {
    @Autowired
    DataServiceVOS5 dataServiceVOS5;
    @Autowired
    DataServiceVOS15 dataServiceVOS15;
    @Autowired
    UserServices userServices;

    @GetMapping("/main/vos5")
    public String mainPageVos5(Model model) {
        sendAuthUserToModel(model);
        List<DataVos5> dataVos5 = dataServiceVOS5.getCurrentDayVos5();
        model.addAttribute("dataVos5", dataVos5);
        return "vos5/mainVos5";
    }



    @GetMapping("/main/vos15")
    public String mainPageVos15(Model model){
        sendAuthUserToModel(model);
        List<DataVos15> data = dataServiceVOS15.getCurrentDayVos15();
        model.addAttribute("dataVos15",data);
        return "vos15/mainVos15";
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        sendAuthUserToModel(model);
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        sendAuthUserToModel(model);
        return "login";
    }



    private static void sendAuthUserToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if("anonymousUser".equalsIgnoreCase(username)) username = "неавторизаванный пользователь";
        model.addAttribute("username", username);
    }
}
