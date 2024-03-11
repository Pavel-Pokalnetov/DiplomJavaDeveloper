package ru.slenergo.AppMonitoring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.slenergo.AppMonitoring.model.DataSummary;
import ru.slenergo.AppMonitoring.model.services.ReportService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.slenergo.AppMonitoring.etc.DataFormats.formatterDateOnly;

@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @GetMapping("")
    public String reportMainPage() {
        return "report/mainReport";
    }

    /**
     * Сводный отчет за текущие сутки
     *
     * @param model
     * @return
     */
    @GetMapping("/summary")
    public String summaryReport(Model model) {
        List<DataSummary> dataSummaryList = reportService.getSummaryReportToDay();
        if(dataSummaryList==null){
            dataSummaryList=new ArrayList<>();
        }
            model.addAttribute(dataSummaryList);
            model.addAttribute("currentdate", LocalDate.now().format(formatterDateOnly));
        return "report/summaryReport";
    }

    /**
     * Обновление сводного отчета за текущие сутки
     *
     * @return
     */
    @GetMapping("/summary/update")
    public String summaryReportUpdate() {
        reportService.recalcSummaryReportByDate(LocalDate.now());
        return "redirect:/report/summary";
    }

    @GetMapping("/dayreports")
    public String dayReports(@RequestParam(required = false) LocalDate date, Model model) {
        if (date == null) {
            model.addAttribute("isReportExist", false);
            model.addAttribute("currentdate", " --- ");
            model.addAttribute("message","Выберите дату отчета");
        } else {
            model.addAttribute("currentdate",date);
            if(reportService.existsReportByDate(date)) {
                System.out.println(1);
                model.addAttribute("isReportExist",true);
                model.addAttribute("dataList", reportService.getSummaryReportByDay(date));
            }else {
                System.out.println(0);
                model.addAttribute("isReportExist", false);
                model.addAttribute("currentdate", "");
                model.addAttribute("message","Нет отчета на этот день "+date.format(formatterDateOnly));
            }
        }
        return "report/dayReports";
    }
}
