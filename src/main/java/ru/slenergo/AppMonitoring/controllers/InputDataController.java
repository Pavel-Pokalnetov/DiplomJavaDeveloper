package ru.slenergo.AppMonitoring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.slenergo.AppMonitoring.model.DataVos5;
import ru.slenergo.AppMonitoring.services.DataService;
import ru.slenergo.AppMonitoring.services.exceptions.PrematureEntryException;

import java.time.LocalDateTime;

@Controller
@RequestMapping
public class InputDataController {
    @Autowired
    DataService ds;

    @GetMapping("/input/vos5")
    public String inputDataVos5() {
        return "/input/vos5";
    }


    @GetMapping("/input/vos15")
    public String inputDataVos15() {
        return "/input/vos15";
    }


    /**
     * Запись данных для ВОС5000 в базу
     */
    @RequestMapping(value = "/input/vos5", method = RequestMethod.POST)
    public String addDataVos5(@RequestParam Double volExtract,
                              @RequestParam LocalDateTime date,
                              @RequestParam Double volCiti,
                              @RequestParam Double volBackCity,
                              @RequestParam Double volBackVos15,
                              @RequestParam Double cleanWaterSupply,
                              @RequestParam Double pressureCity,
                              @RequestParam Double pressureBackCity,
                              @RequestParam Double pressureBackVos15,
                              Model model) {

        DataVos5 dataVos5;
        try {
            dataVos5 = ds.createDataVos5(
                    date,
                    volExtract,
                    volCiti,
                    volBackCity,
                    volBackVos15,
                    cleanWaterSupply,
                    pressureCity,
                    pressureBackCity,
                    pressureBackVos15);
            model.addAttribute("result",
                    ds.saveDataToDbVos5(dataVos5) ?
                            "Запись добавлена" :
                            "Ошибка записи. Обратитесь к администратру.");

        } catch (PrematureEntryException e) {
            // если данные за текущий час уже были добавлены в базу
            model.addAttribute("result",
                    e.getMessage());
        }
        model.addAttribute("url", "/main/vos5");
        return "/input/result";
    }

    @PostMapping("/update/vos5/{id}")
    public String updateDataVos5(@PathVariable Long id,
                                 @RequestParam Long stationId,
                                 @RequestParam Long userId,
                                 @RequestParam LocalDateTime date,
                                 @RequestParam Double volExtract,
                                 @RequestParam Double volCiti,
                                 @RequestParam Double volBackCity,
                                 @RequestParam Double volBackVos15,
                                 @RequestParam Double cleanWaterSupply,
                                 @RequestParam Double deltaCleanWaterSupply,
                                 @RequestParam Double pressureCity,
                                 @RequestParam Double pressureBackCity,
                                 @RequestParam Double pressureBackVos15,
                                 Model model) {

        DataVos5 dataVos5 = ds.createDataVos5(id,
                stationId,
                userId,
                date,
                volExtract,
                volCiti,
                volBackCity,
                volBackVos15,
                cleanWaterSupply,
                deltaCleanWaterSupply,
                pressureCity,
                pressureBackCity,
                pressureBackVos15);

        if (ds.updateDataVos5(dataVos5)) {
            model.addAttribute("result", "Данные обновлены\n"+dataVos5);
        } else {
            model.addAttribute("result", "Приобновлении данных произошла ошибка");
        }
        return "/input/result";
    }

    /** форма для изменения записи ВОС5
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/update/vos5/{id}")
    public String updateFormVos5(@PathVariable long id, Model model) {
        DataVos5 dataVos5 = ds.getDataVosById(id);
        if (dataVos5 == null) return "redirect:/main/vos5";
        System.out.println(dataVos5);
        model.addAttribute("entity", dataVos5);
        return "/update/vos5";
    }

}
