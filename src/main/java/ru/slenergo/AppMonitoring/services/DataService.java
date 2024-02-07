package ru.slenergo.AppMonitoring.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.slenergo.AppMonitoring.model.DataVos5;
import ru.slenergo.AppMonitoring.repository.DataRepositoryVos15;
import ru.slenergo.AppMonitoring.repository.DataRepositoryVos5;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    @Autowired
    DataRepositoryVos5 dataRep5;
    @Autowired
    DataRepositoryVos15 dataRep15;

    /**
     * Получить все данные
     *
     * @return
     */
    public List<DataVos5> getAllVos5() {
        List<DataVos5> dataVos5 = new ArrayList<>();
        dataRep5.findAll().iterator().forEachRemaining(dataVos5::add);
        return dataVos5;
    }

    /**
     * Получить данные за последние 31 день
     *
     * @return
     */
    public List<DataVos5> getLastMontVos5() {

        List<DataVos5> dataVos5 = new ArrayList<>();
        dataRep5.findLastMont().iterator().forEachRemaining(dataVos5::add);
        return dataVos5;
    }

    /**
     * Получить последнюю запись
     *
     * @return
     */
    public DataVos5 getLastDataItem() {

        DataVos5 data = dataRep5.findLastItem();
        return data;
    }


    public boolean saveDataToDb(DataVos5 dataVos5) {
        try {
            Double lostCleanWaterSupply = 0.0;
            DataVos5 dataLost = getLastDataItem();
            if (dataLost != null) lostCleanWaterSupply = dataLost.getCleanWaterSupply();
            dataVos5.setDeltaCleanWaterSupply(dataVos5.getCleanWaterSupply() - lostCleanWaterSupply);
            dataRep5.save(dataVos5);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DataVos5 createDataVos5(Double volExtract, Double volCiti,
                                   Double volBackCity, Double volBackVos15, Double cleanWaterSupply,
                                   Double pressureCity, Double pressureBackCity, Double pressureBackVos15) {
        DataVos5 dataVos5 = new DataVos5();
        dataVos5.setDate(LocalDateTime.now());
        dataVos5.setVolExtract(volExtract);
        dataVos5.setVolCiti(volCiti);
        dataVos5.setVolBackCity(volBackCity);
        dataVos5.setVolBackVos15(volBackVos15);
        dataVos5.setCleanWaterSupply(cleanWaterSupply);
        dataVos5.setPressureCity(pressureCity);
        dataVos5.setPressureBackCity(pressureBackCity);
        dataVos5.setPressureBackVos15(pressureBackVos15);

        Double lostCleanWaterSupply = 0.0;
        DataVos5 dataLost = getLastDataItem();
        if (dataLost != null) {
            System.out.println(dataLost);
            lostCleanWaterSupply = dataLost.getCleanWaterSupply();
        }

        dataVos5.setDeltaCleanWaterSupply(dataVos5.getCleanWaterSupply() - lostCleanWaterSupply);

        return dataVos5;
    }
}
