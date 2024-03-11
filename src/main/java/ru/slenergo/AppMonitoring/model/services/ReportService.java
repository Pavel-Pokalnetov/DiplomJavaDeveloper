package ru.slenergo.AppMonitoring.model.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.slenergo.AppMonitoring.model.DataSummary;
import ru.slenergo.AppMonitoring.model.DataVos15;
import ru.slenergo.AppMonitoring.model.DataVos5;
import ru.slenergo.AppMonitoring.model.repository.DataRepositoryVos5;
import ru.slenergo.AppMonitoring.model.repository.DataRepositorySummary;
import ru.slenergo.AppMonitoring.model.repository.DataRepositoryVos15;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    DataRepositoryVos5 dataRepositoryVos5;
    @Autowired
    DataRepositoryVos15 dataRepositoryVos15;
    @Autowired
    DataRepositorySummary dataRepositorySummary;

    public DataSummary buildDataSummary(DataVos5 dataVos5, DataVos15 dataVos15) {
        if (dataVos5 == null || dataVos15 == null) return null;
//        if (dataVos5.getDate().equals(dataVos15.getDate())) return null;
        DataSummary dataSummary = new DataSummary();
        dataSummary.setDate(dataVos5.getDate());
        dataSummary.setVolExtract(dataVos5.getVolExtract() + dataVos15.getVolExtract());
        dataSummary.setVolCiti(dataVos5.getVolAll() + dataVos15.getVolCity());
        dataSummary.setCleanWaterSupply(dataVos5.getCleanWaterSupply() + dataVos15.getCleanWaterSupply());
        dataSummary.setDeltaCleanWaterSupply(dataVos5.getDeltaCleanWaterSupply() + dataVos15.getDeltaCleanWaterSupply());
        return dataSummary;
    }

    public void saveDataSummaryOneRecord(LocalDateTime date) {
        DataSummary dataSummary = buildDataSummary(dataRepositoryVos5.getDataVos5ByDate(date), dataRepositoryVos15.getDataVos15ByDate(date));
        if (dataSummary != null) dataRepositorySummary.save(dataSummary);
    }

    /**
     * Получить отчет за текущий день
     *
     * @return
     */
    public List<DataSummary> getSummaryReportToDay() {
        return getSummaryReportByDay(LocalDate.now());
    }

    /**
     * Перерасчет сводного отчета на указанную дату
     *
     * @param date - дата LocalDateTime
     */
    public void recalcSummaryReportByDate(LocalDateTime date) {
        date = date.truncatedTo(ChronoUnit.DAYS);
        dataRepositorySummary.deleteDataSummaryByDateBetween(date, date.plusHours(23));
        for (int i = 0; i < 24; i++) {
            saveDataSummaryOneRecord(date.plusHours(i));
        }
    }

    /**
     * Перерасчет сводного отчета на указанную дату
     *
     * @param date - дата DateTime
     */
    public void recalcSummaryReportByDate(LocalDate date) {
        recalcSummaryReportByDate(date.atStartOfDay());
    }


    /**
     * Проверка существования отчета на указанную дату
     *
     * @param date -дата LocalDateTime
     * @return
     */
    public boolean existsReportByDate(LocalDate date) {
        LocalDateTime localDate = date.atStartOfDay();
        return dataRepositorySummary.existsByDateBetween(localDate, localDate.plusHours(23));
    }

    /**
     * Получить сводный отчет на указанную дату
     *
     * @param date
     * @return
     */
    public List<DataSummary> getSummaryReportByDay(LocalDate date) {
        if (existsReportByDate(date)) {
            LocalDateTime dateBegin = date.atStartOfDay();
            LocalDateTime dateEnd = dateBegin.plusHours(23);
            return dataRepositorySummary.getDataSummaryByDateBetween(dateBegin, dateEnd);
        }
        return null;
    }
}


