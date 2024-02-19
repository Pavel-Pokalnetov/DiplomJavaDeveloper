package ru.slenergo.AppMonitoring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.slenergo.AppMonitoring.services.DataServiceVOS15;
import ru.slenergo.AppMonitoring.services.DataServiceVOS5;
import ru.slenergo.AppMonitoring.services.UserServices;

@RestController
public class TestController {
    @Autowired
    UserServices us;
    @Autowired
    DataServiceVOS5 ds5;
    @Autowired
    DataServiceVOS15 ds15;

    @RequestMapping("/test")
    public String testRequest(){
        return ds5.getLastDataItemVos5().toString();
    }
}
