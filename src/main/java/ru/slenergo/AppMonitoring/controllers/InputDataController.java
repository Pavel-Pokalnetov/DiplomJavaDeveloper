package ru.slenergo.AppMonitoring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.slenergo.AppMonitoring.model.DataVos15;
import ru.slenergo.AppMonitoring.model.DataVos5;
import ru.slenergo.AppMonitoring.services.DataServiceVOS15;
import ru.slenergo.AppMonitoring.services.DataServiceVOS5;
import ru.slenergo.AppMonitoring.services.exceptions.PrematureEntryException;

import java.time.LocalDateTime;

@Controller
@RequestMapping
public class InputDataController {
    @Autowired
    DataServiceVOS5 dataServiceVOS5;
    @Autowired
    DataServiceVOS15 dataServiceVOS15;

    /**
     * Форма ввода записи для ВОС5000
     */
    @GetMapping("/input/vos5")
    public String inputDataVos5() {
        return "/vos5/inputVos5";
    }

    /**
     * Форма ввода записи для ВОС15000
     */
    @GetMapping("/input/vos15")
    public String inputDataVos15() {
        return "/vos15/inputVos15";
    }


    /**
     * Запись новых данных для ВОС5000 в базу
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
            dataVos5 = dataServiceVOS5.createDataVos5(
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
                    dataServiceVOS5.saveDataToDbVos5(dataVos5) ?
                            "Запись добавлена" :
                            "Ошибка записи. Обратитесь к администратру.");

        } catch (PrematureEntryException e) {
            // если данные за текущий час уже были добавлены в базу
            model.addAttribute("result",
                    e.getMessage());
        }
        model.addAttribute("url", "/main/vos5");
        return "/result";
    }

    @RequestMapping(value = "/input/vos15", method = RequestMethod.POST)
    public String addDataVos15(@RequestParam Double volExtract,
                              @RequestParam LocalDateTime date,
                              @RequestParam Double volCiti,
                              @RequestParam Double cleanWaterSupply,
                              @RequestParam Double pressureCity,
                              Model model) {

        DataVos15 dataVos15;
        try {
            dataVos15 = dataServiceVOS15.createDataVos15(
                    date,
                    volExtract,
                    volCiti,
                    cleanWaterSupply,
                    pressureCity);
            model.addAttribute("result",
                    dataServiceVOS15.saveDataToDbVos15(dataVos15) ?
                            "Запись добавлена" :
                            "Ошибка записи. Обратитесь к администратру.");

        } catch (PrematureEntryException e) {
            // если данные за текущий час уже были добавлены в базу
            model.addAttribute("result",
                    e.getMessage());
        }
        model.addAttribute("url", "/main/vos15");
        return "/result";
    }

    @PostMapping("/update/vos5/{id}")
    public String updateDataVos5(@PathVariable Long id,
                                 @RequestParam Long userId,
                                 @RequestParam LocalDateTime date,
                                 @RequestParam Double volExtract,
                                 @RequestParam Double volCiti,
                                 @RequestParam Double volBackCity,
                                 @RequestParam Double volBackVos15,
                                 @RequestParam Double cleanWaterSupply,
                                 @RequestParam Double pressureCity,
                                 @RequestParam Double pressureBackCity,
                                 @RequestParam Double pressureBackVos15,
                                 Model model) {
        if (userId == null) userId = 0L;
        DataVos5 dataVos5 = dataServiceVOS5.createDataVos5(id,
                userId,
                date,
                volExtract,
                volCiti,
                volBackCity,
                volBackVos15,
                cleanWaterSupply,
                pressureCity,
                pressureBackCity,
                pressureBackVos15);

        if (dataServiceVOS5.updateDataVos5(dataVos5)) {
            model.addAttribute("result", "Данные обновлены\n");
        } else {
            model.addAttribute("result", "Приобновлении данных произошла ошибка");
        }
        model.addAttribute("url", "/main/vos5");
        return "/result";
    }

    @PostMapping("/update/vos15/{id}")
    public String updateDataVos15(@PathVariable Long id,
                                 @RequestParam Long userId,
                                 @RequestParam LocalDateTime date,
                                 @RequestParam Double volExtract,
                                 @RequestParam Double volCiti,
                                 @RequestParam Double cleanWaterSupply,
                                 @RequestParam Double pressureCity,
                                 Model model) {
        if (userId == null) userId = 0L;
        DataVos15 dataVos15 = dataServiceVOS15.createDataVos15(id,
                userId,
                date,
                volExtract,
                volCiti,
                cleanWaterSupply,
                pressureCity);

        if (dataServiceVOS15.updateDataVos15(dataVos15)) {
            model.addAttribute("result", "Данные обновлены\n");
        } else {
            model.addAttribute("result", "Приобновлении данных произошла ошибка");
        }
        model.addAttribute("url", "/main/vos15");
        return "/result";
    }



    /**
     * форма для изменения записи ВОС15000
     *
     * @param id - id записи
     */
    @GetMapping("/update/vos5/{id}")
    public String updateFormVos5(@PathVariable long id, Model model) {
        DataVos5 dataVos5 = dataServiceVOS5.getDataVos5ById(id);
        if (dataVos5 == null) return "redirect:/main/vos5";
        model.addAttribute("entity", dataVos5);
        return "/vos5/updateVos5";
    }

    /**
     * форма для изменения записи ВОС15000
     *
     * @param id - id записи
     */
    @GetMapping("/update/vos15/{id}")
    public String updateFormVos15(@PathVariable long id, Model model) {
        DataVos15 dataVos15 = dataServiceVOS15.getDataVos15ById(id);
        if (dataVos15 == null) return "redirect:/main/vos15";
        model.addAttribute("entity", dataVos15);
        return "/vos15/updateVos15";
    }

}
