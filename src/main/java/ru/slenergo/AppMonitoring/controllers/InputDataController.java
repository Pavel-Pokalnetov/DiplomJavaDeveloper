package ru.slenergo.AppMonitoring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.slenergo.AppMonitoring.model.DataVos15;
import ru.slenergo.AppMonitoring.model.DataVos5;
import ru.slenergo.AppMonitoring.services.DataServiceVOS15;
import ru.slenergo.AppMonitoring.services.DataServiceVOS5;
import ru.slenergo.AppMonitoring.exceptions.PrematureEntryException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Controller
public class InputDataController {
    @Autowired
    DataServiceVOS5 dataServiceVOS5;
    @Autowired
    DataServiceVOS15 dataServiceVOS15;


    /**
     * Форма ввода записи для ВОС5000
     */
    @GetMapping("/input/vos5/**")
    @PreAuthorize("hasAnyRole('ADMIN','VOS5')")
    public String inputDataVos5() {
        return "vos5/inputVos5";
    }


    /**
     * Запись новых данных для ВОС5000 в базу
     */
    @RequestMapping(value = "/input/vos5", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ADMIN','VOS5')")
    public String addDataVos5(@RequestParam Double volExtract,
                              @RequestParam LocalDateTime date,
                              @RequestParam Double volCity,
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
                    volCity,
                    volBackCity,
                    volBackVos15,
                    cleanWaterSupply,
                    pressureCity,
                    pressureBackCity,
                    pressureBackVos15);
            model.addAttribute("result",
                    dataServiceVOS5.saveDataVos5(dataVos5) ?
                            "Запись добавлена" :
                            "Ошибка записи. Обратитесь к администратору.");
        } catch (PrematureEntryException e) {
            model.addAttribute("result",
                    e.getMessage());
        }
        model.addAttribute("url", "/main/vos5");
        return "result";
    }


    /**
     * форма для изменения записи ВОС15000
     *
     * @param id - id записи
     */
    @GetMapping("/update/vos5/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VOS5')")
    public String updateFormVos5(@PathVariable long id, Model model) {
        DataVos5 dataVos5 = dataServiceVOS5.getDataVos5ById(id);
        if (dataVos5 == null) return "redirect:/main/vos5";
        model.addAttribute("entity", dataVos5);
        return "vos5/updateVos5";
    }

    /**
     * Обновление данных из формы
     */
    @RequestMapping(value = "/update/vos5", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ADMIN','VOS5')")
    public String updateDataVos5(@RequestParam Long id,
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
        if (dataServiceVOS5.updateAndSaveDataVos5ByDate(
                id, userId,
                date, volExtract,
                volCiti, volBackCity, volBackVos15,
                cleanWaterSupply,
                pressureCity, pressureBackCity, pressureBackVos15)) {
            model.addAttribute("result", "Данные обновлены\n");
        } else {
            model.addAttribute("result", "При обновлении данных произошла ошибка");
        }
        model.addAttribute("url", "/main/vos5");
        return "result";
    }

    /**
     * Форма ввода записи для ВОС15000
     */
    @GetMapping("/input/vos15/**")
    @PreAuthorize("hasAnyRole('ADMIN','VOS15')")
    public String inputDataVos15() {
        return "vos15/inputVos15";
    }

    @RequestMapping(value = "/input/vos15", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ADMIN','VOS15')")
    public String addDataVos15(@RequestParam LocalDateTime date,
                               @RequestParam Double volExtract,
                               @RequestParam Double volLeftCity,
                               @RequestParam Double volRightCity,
                               @RequestParam Double cleanWaterSupply,
                               @RequestParam Double pressureCity,
                               Model model) {
        DataVos15 dataVos15;

        try {
            dataVos15 = dataServiceVOS15.createDataVos15(
                    date,
                    volExtract,
                    volLeftCity, volRightCity,
                    cleanWaterSupply,
                    pressureCity);
            model.addAttribute("result",
                    dataServiceVOS15.saveDataVos15(dataVos15) ?
                            "Запись добавлена" :
                            "Ошибка записи. Обратитесь к администратору.");
        } catch (PrematureEntryException e) {
            // если данные за текущий час уже были добавлены в базу
            model.addAttribute("result",
                    e.getMessage());
        }
        model.addAttribute("url", "/main/vos15");
        return "result";
    }

    @PostMapping("/update/vos15/**")
    @PreAuthorize("hasAnyRole('ADMIN','VOS15')")
    public String updateDataVos15(@RequestParam Long id,
                                  @RequestParam Long userId,
                                  @RequestParam LocalDateTime date,
                                  @RequestParam Double volExtract,
                                  @RequestParam Double volLeftCity,
                                  @RequestParam Double volRightCity,
                                  @RequestParam Double cleanWaterSupply,
                                  @RequestParam Double pressureCity,
                                  Model model) {
        date = date.truncatedTo(ChronoUnit.HOURS);
        if (userId == null) userId = 0L;
        DataVos15 dataVos15 = dataServiceVOS15.createDataVos15(id,
                userId,
                date,
                volExtract,
                volLeftCity,volRightCity,
                cleanWaterSupply,
                pressureCity);
        if (dataServiceVOS15.updateAndSaveDataVos15ByDate(dataVos15)) {
            model.addAttribute("result", "Данные обновлены\n");
        } else {
            model.addAttribute("result", "Приобновлении данных произошла ошибка");
        }
        model.addAttribute("url", "/main/vos15");
        return "result";
    }


    /**
     * форма для изменения записи ВОС15000
     *
     * @param id - id записи
     */
    @GetMapping("/update/vos15/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VOS15')")
    public String updateFormVos15(@PathVariable long id, Model model) {
        DataVos15 dataVos15 = dataServiceVOS15.getDataVos15ById(id);
        if (dataVos15 == null) return "redirect:/main/vos15";
        model.addAttribute("entity", dataVos15);
        return "vos15/updateVos15";
    }
}