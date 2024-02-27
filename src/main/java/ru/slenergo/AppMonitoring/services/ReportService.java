package ru.slenergo.AppMonitoring.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.slenergo.AppMonitoring.model.DataSummary;
import ru.slenergo.AppMonitoring.model.DataVos15;
import ru.slenergo.AppMonitoring.model.DataVos5;
import ru.slenergo.AppMonitoring.repository.DataRepositorySummary;
import ru.slenergo.AppMonitoring.repository.DataRepositoryVos15;
import ru.slenergo.AppMonitoring.repository.DataRepositoryVos5;

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
        System.out.println(dataVos5);
        System.out.println(dataVos15);
        if (dataVos5 == null || dataVos15 == null) return null;
//        if (dataVos5.getDate().equals(dataVos15.getDate())) return null;
        DataSummary dataSummary = new DataSummary();
        dataSummary.setDate(dataVos5.getDate());
        dataSummary.setVolExtract(dataVos5.getVolExtract() + dataVos15.getVolExtract());
        dataSummary.setVolCiti(dataVos5.getVolAll() + dataVos15.getVolCity());
        dataSummary.setCleanWaterSupply(dataVos5.getCleanWaterSupply() + dataVos15.getCleanWaterSupply());
        dataSummary.setDeltaCleanWaterSupply(dataVos5.getDeltaCleanWaterSupply() + dataVos15.getDeltaCleanWaterSupply());
        System.out.println(dataSummary);
        return dataSummary;
    }

    public void saveDataSummaryOneRecord(LocalDateTime date) {
        DataSummary dataSummary = buildDataSummary(
                dataRepositoryVos5.getDataVos5ByDate(date),
                dataRepositoryVos15.getDataVos15ByDate(date));
        System.out.println(dataSummary);
        if (dataSummary != null) dataRepositorySummary.save(dataSummary);
    }

    public List<DataSummary> getSummaryReportToDay() {
        LocalDateTime dateBegin = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime dateEnd = dateBegin.plusHours(23);
        return dataRepositorySummary.getDataSummaryByDateBetween(dateBegin, dateEnd);
    }

    public void recalcSummaryReportByDate(LocalDateTime date) {
        date = date.truncatedTo(ChronoUnit.DAYS);
        dataRepositorySummary.deleteDataSummaryByDateBetween(date, date.plusHours(23));
        for (int i = 0; i < 24; i++) {
            saveDataSummaryOneRecord(date.plusHours(i));
        }
    }


}


