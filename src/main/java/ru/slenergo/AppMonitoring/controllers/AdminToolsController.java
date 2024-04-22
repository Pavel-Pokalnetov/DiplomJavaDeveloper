package ru.slenergo.AppMonitoring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.slenergo.AppMonitoring.model.services.ReportService;

import java.time.LocalDate;

@Controller
public class AdminToolsController {
    ReportService reportService;

    public AdminToolsController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/admin/fullupdatesummary")
    public String summaryReportFullUpdateGET() {
        return "report/fullUpdateSynnaryReportsFromDateToDate";
    }

    @PostMapping("/admin/fullupdatesummary")
    public String summaryReportFullUpdatePOST(@RequestParam(required = false) LocalDate date1,
                                              @RequestParam(required = false) LocalDate date2,
                                              Model model) {

        if (date1 != null && date2 == null) {
            date2 = LocalDate.now();
        }
        System.out.println(date1 + " -- " + date2);
        model.addAttribute("report", reportService.recalcSummaryReportFromDateToDate(date1, date2));

        return "report/fullUpdateSynnaryReportsFromDateToDate";
    }

    @GetMapping("/admin")
    public String adminIndexPage() {
        return "adminTools/admin";
    }


}
