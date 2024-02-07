package ru.slenergo.AppMonitoring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.slenergo.AppMonitoring.model.DataVos5;

@Controller
@RequestMapping
public class ImputDataController {

    @GetMapping("/input/vos5")
    public String inputDataVos5() {
        return "inputdataVos5";
    }

    @GetMapping("/input/vos15")
    public String inputDataVos15() {
        return "inputdataVos15";
    }

    @PostMapping("/input/vos5")
    public String addDataVos5(@RequestParam DataVos5 dataVos5) {
        System.out.println(dataVos5);
        return "redirect:/main/vos5";
    }


}
