package ru.slenergo.AppMonitoring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.slenergo.AppMonitoring.model.DataVos15;
import ru.slenergo.AppMonitoring.model.DataVos5;
import ru.slenergo.AppMonitoring.model.services.DataServiceVOS15;
import ru.slenergo.AppMonitoring.model.services.DataServiceVOS5;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryPageController {
    @Autowired
    DataServiceVOS5 dataServiceVOS5;
    @Autowired
    DataServiceVOS15 dataServiceVOS15;

    @GetMapping("/vos5")
    public String historyPageVos5(@RequestParam(required = false) LocalDate date, Model model){
        if(date==null){
            //неуказана дата
            model.addAttribute(model.addAttribute("selectedDate",LocalDateTime.now()));
            model.addAttribute("message","Выберите дату ");
            model.addAttribute("dataExists",false);
            return "history/historyVos5";
        }
            //указана дата
        List<DataVos5> dataVos5s  = dataServiceVOS5.getDataVos5ByDay(date.atStartOfDay());
        model.addAttribute("selectedDate",date);
        if (dataVos5s==null ||dataVos5s.isEmpty()){
            //но нет данных
            model.addAttribute("message","Нет записей за указанную дату");
            model.addAttribute("dataExists",false);
            return "history/historyVos5";
        }
        model.addAttribute("message","");
        model.addAttribute("dataExists",true);
        model.addAttribute("dataVos5",dataVos5s);
        return "history/historyVos5";
    }

    @GetMapping("/vos15")
    public String historyPageVos15(@RequestParam(required = false) LocalDate date, Model model){
        if(date==null){
            //неуказана дата
            model.addAttribute(model.addAttribute("selectedDate",LocalDateTime.now()));
            model.addAttribute("message","Выберите дату ");
            model.addAttribute("dataExists",false);
            return "history/historyVos15";
        }
        //указана дата
        List<DataVos15> dataVos15s  = dataServiceVOS15.getDataVos15ByDay(date.atStartOfDay());
        model.addAttribute("selectedDate",date);
        if (dataVos15s==null ||dataVos15s.isEmpty()){
            //но нет данных
            model.addAttribute("message","Нет записей за указанную дату");
            model.addAttribute("dataExists",false);
            return "history/historyVos15";
        }
        model.addAttribute("message","");
        model.addAttribute("dataExists",true);
        model.addAttribute("dataVos15",dataVos15s);
        return "history/historyVos15";
    }




}
