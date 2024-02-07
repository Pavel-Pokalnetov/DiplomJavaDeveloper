package ru.slenergo.AppMonitoring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.slenergo.AppMonitoring.model.DataVos5;
import ru.slenergo.AppMonitoring.services.DataService;

@Controller
@RequestMapping
public class ImputDataController {
    @Autowired
    DataService ds;

    @GetMapping("/input/vos5")
    public String inputDataVos5() {
        return "inputdataVos5";
    }

    @GetMapping("/input/vos5result")
    public String resultInputDataVos5() {
        return "result";
    }

    @GetMapping("/input/vos15")
    public String inputDataVos15() {
        return "inputdataVos15";
    }


    @RequestMapping(value = "/input/vos5", method = RequestMethod.POST)
    public String addDataVos5(@RequestParam Double volExtract,
                              @RequestParam Double volCiti,
                              @RequestParam Double volBackCity,
                              @RequestParam Double volBackVos15,
                              @RequestParam Double cleanWaterSupply,
                              @RequestParam Double pressureCity,
                              @RequestParam Double pressureBackCity,
                              @RequestParam Double pressureBackVos15,
                              Model model) {
        DataVos5 dataVos5 = ds.createDataVos5(
                volExtract,
                volCiti,
                volBackCity,
                volBackVos15,
                cleanWaterSupply,
                pressureCity,
                pressureBackCity,
                pressureBackVos15);
        model.addAttribute("result", ds.saveDataToDb(dataVos5) ? "Запись добавлена" : "Ошибка записи. Обратитесь к администратру.");
        model.addAttribute("url","/main/vos5");
        return "result";
    }


}
