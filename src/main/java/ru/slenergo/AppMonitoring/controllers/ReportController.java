package ru.slenergo.AppMonitoring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.slenergo.AppMonitoring.model.DataSummary;
import ru.slenergo.AppMonitoring.services.ReportService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ReportController {
    @Autowired
    ReportService reportService;

    /** Сводный отчет за текущие сутки
     * @param model
     * @return
     */
    @GetMapping("/report/summary")
    public String summaryReport(Model model){
        List<DataSummary> dataSummaryList = reportService.getSummaryReportToDay();
        model.addAttribute(dataSummaryList);
        return "/report/reportSymmary";
    }

    @GetMapping("/report/summary/update")
    public String summaryReportUpdate(){
        reportService.recalcSummaryReportByDate(LocalDateTime.now());
        return "redirect:/report/summary";
    }
}
