package ru.slenergo.AppMonitoring.services;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.slenergo.AppMonitoring.model.DataVos5;
import ru.slenergo.AppMonitoring.repository.DataRepositoryVos5;
import ru.slenergo.AppMonitoring.services.exceptions.PrematureEntryException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DataServiceVOS5 {
    @Autowired
    DataRepositoryVos5 dataRep5;
    @Autowired
    ReportService reportService;

    /**
     * Получить данные для ВОС5000 за последние 24 часа
     */
    public List<DataVos5> getCurrentDayVos5() {
        return dataRep5.findDataVos5sByDateIsAfterOrderByDateAsc(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
    }

    /**
     * Получить последнюю запись для ВОС5000
     */
    public DataVos5 getLastDataItemVos5() {
        return dataRep5.findLastItem();
    }

    /**
     * Запись в базу данных для ВОС5000
     */
    @Transactional
    public boolean saveDataToDbVos5(DataVos5 dataVos5) {
        try {
            dataRep5.saveAndFlush(dataVos5);
            updateNextDataCleanWaterSupply(dataVos5);
            reportService.saveDataSummaryOneRecord(dataVos5.getDate());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Обновление поля роста запаса чистой воды в следующей по времени записи
     *
     * @param dataVos5 - текущая запись
     */
    private void updateNextDataCleanWaterSupply(DataVos5 dataVos5) {
        DataVos5 nextData = dataRep5.getNextData(dataVos5.getDate());
        if (nextData != null) {
            nextData.setDeltaCleanWaterSupply(nextData.getCleanWaterSupply() - dataVos5.getCleanWaterSupply());
            dataRep5.saveAndFlush(nextData);
        }
    }

    /**
     * Создание записи по данным из формы с проверкой на повторный ввод в текущем часе
     *
     * @param date              - дата
     * @param volExtract        - объем добычи
     * @param volCiti           - отдача в город
     * @param volBackCity       - обратка из города
     * @param volBackVos15      - обратка от ВОС15000
     * @param cleanWaterSupply  - запас чистой воды
     * @param pressureCity      - давление в трубопроводе в город
     * @param pressureBackCity  - давление в трубопроводе обратка из города
     * @param pressureBackVos15 - давление в трубопроводе обратка от ВОС15000
     * @return DataVos5
     * @throws PrematureEntryException
     */
    public DataVos5 createDataVos5(LocalDateTime date, Double volExtract, Double volCiti,
                                   Double volBackCity, Double volBackVos15, Double cleanWaterSupply,
                                   Double pressureCity, Double pressureBackCity, Double pressureBackVos15)
            throws PrematureEntryException {
        date = date.truncatedTo(ChronoUnit.HOURS); //усечение времени до часа (отбрасываем все что меньше часа)
        if (dataRep5.existsByDate(date)) {
            //проверка на повторное добавление записи
            throw new PrematureEntryException(date);
        }
        DataVos5 dataVos5 = new DataVos5();
        DataVos5 dataPrev = dataRep5.getPrevData(date); //находим предыдущую запись
        Double lostCleanWaterSupply = 0.0; //предыдущий запас воды
        if (dataPrev != null) {
            // если запись есть по устанавливаем новое значение предыдущего запаса воды
            lostCleanWaterSupply = dataPrev.getCleanWaterSupply();
        }

        dataVos5.setUserId(1L);
        dataVos5.setDate(date);
        dataVos5.setVolExtract(volExtract);
        dataVos5.setVolCity(volCiti);
        dataVos5.setVolBackCity(volBackCity);
        dataVos5.setVolBackVos15(volBackVos15);
        dataVos5.setCleanWaterSupply(cleanWaterSupply);
        dataVos5.setPressureCity(pressureCity);
        dataVos5.setPressureBackCity(pressureBackCity);
        dataVos5.setPressureBackVos15(pressureBackVos15);
        dataVos5.setDeltaCleanWaterSupply(dataVos5.getCleanWaterSupply() - lostCleanWaterSupply);
        return dataVos5;
    }

    public boolean updateDataVos5(
            Long id, Long userId,
            LocalDateTime date,
            Double volExtract, Double volCiti,Double volBackCity,Double volBackVos15,
            Double cleanWaterSupply,
            Double pressureCity,Double pressureBackCity,Double pressureBackVos15){
        DataVos5 updateData =  dataRep5.getDataVos5ByDate(date);
        return updateDataVos5(updateData);
    }

    @Transactional
    public boolean updateDataVos5(DataVos5 dataVos5) {
        try {
            dataRep5.saveAndFlush(dataVos5);
            updateNextDataCleanWaterSupply(dataVos5); //обновляем значение раста запаса РЧВ в следующей по времени, существующей записи
            reportService.saveDataSummaryOneRecord(dataVos5.getDate());//обновляем соответствующую запись сводного отчета
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DataVos5 getDataVos5ById(long id) {
        return dataRep5.findById(id).orElse(null);
    }

    public DataVos5 createDataVos5(Long id,
                                   Long userId,
                                   LocalDateTime date,
                                   Double volExtract,
                                   Double volCiti,
                                   Double volBackCity,
                                   Double volBackVos15,
                                   Double cleanWaterSupply,
                                   Double pressureCity,
                                   Double pressureBackCity,
                                   Double pressureBackVos15) {
        DataVos5 prevData = dataRep5.getPrevData(date);
        double deltaCleanWaterSupply = 0;
        if (prevData != null) deltaCleanWaterSupply = cleanWaterSupply - prevData.getCleanWaterSupply();

        return new DataVos5(id, userId, date,
                volExtract, volCiti,
                volBackCity, volBackVos15,
                cleanWaterSupply, deltaCleanWaterSupply,
                pressureCity, pressureBackCity, pressureBackVos15);
    }
}
