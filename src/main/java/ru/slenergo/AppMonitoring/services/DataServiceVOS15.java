package ru.slenergo.AppMonitoring.services;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.slenergo.AppMonitoring.model.DataVos15;
import ru.slenergo.AppMonitoring.repository.DataRepositoryVos15;
import ru.slenergo.AppMonitoring.repository.DataRepositoryVos15;
import ru.slenergo.AppMonitoring.services.exceptions.PrematureEntryException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataServiceVOS15 {

    @Autowired
    DataRepositoryVos15 dataRep15;

    /** Получить все данные для ВОС15000
     */
    public List<DataVos15> getAllVos15() {
        List<DataVos15> dataVos15 = new ArrayList<>();
        dataRep15.findAll().iterator().forEachRemaining(dataVos15::add);
        return dataVos15;
    }

    /** Получить данные для ВОС15000 за последние 24 часа
     */
    public List<DataVos15> getLastDayVos15() {

        List<DataVos15> dataVos15 = new ArrayList<>();
        dataRep15.findLastDay().iterator().forEachRemaining(dataVos15::add);
        return dataVos15;
    }

    /** Получить последнюю запись для ВОС15000
     */
    public DataVos15 getLastDataItemVos15() {
        DataVos15 data = dataRep15.findLastItem();
        return data;
    }


    /** Запись в базу данных для ВОС15000000
     */
    @Transactional
    public boolean saveDataToDbVos15(DataVos15 dataVos15) {
        try {
            dataRep15.saveAndFlush(dataVos15);
            updateNextDataCleanWaterSupply(dataVos15);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Обновление поля роста запаса чистой воды в следующей по времени записи
     * @param dataVos15 - текущая запись
     */
    private void updateNextDataCleanWaterSupply(DataVos15 dataVos15) {
        DataVos15 nextData = dataRep15.getNextData(dataVos15.getDate());
        if( nextData != null){
            nextData.setDeltaCleanWaterSupply(nextData.getCleanWaterSupply()- dataVos15.getCleanWaterSupply());
            dataRep15.saveAndFlush(nextData);
        }
    }

    /**
     * Создание записи по данным из формы с проверкой на повторный ввод в текущем часе
     *
     * @param date - дата
     * @param volExtract - объем добычи
     * @param volCiti - отдача в город
     * @param volBackCity - обратка из города
     * @param volBackVos15 - обратка от ВОС15000
     * @param cleanWaterSupply - запас чистой воды
     * @param pressureCity - давление в трубопроводе в город
     * @param pressureBackCity - давление в трубопроводе обратка из города
     * @param pressureBackVos15 - давление в трубопроводе обратка от ВОС15000
     * @return DataVos15
     * @throws PrematureEntryException
     */
    public DataVos15 createDataVos15(LocalDateTime date, Double volExtract, Double volCiti,
                                   Double volBackCity, Double volBackVos15, Double cleanWaterSupply,
                                   Double pressureCity, Double pressureBackCity, Double pressureBackVos15)
            throws PrematureEntryException {

        date = date.truncatedTo(ChronoUnit.HOURS); //усечение времени до часа (отбрасываем все что меньше часа)
        if (dataRep15.existsByDate(date)) {
            throw new PrematureEntryException(date);
        }
        DataVos15 dataVos15 = new DataVos15();

        /* проверка на повторное добавление записи в текущем часе*/

        DataVos15 dataPrev = dataRep15.getPrevData(date); //находим предыдущую запись
        Double lostCleanWaterSupply = 0.0; //предыдущий запас воды
        if (dataPrev != null) {
            // если запись есть по устанавливаем новое значение предыдущего запаса воды
            lostCleanWaterSupply = dataPrev.getCleanWaterSupply();
        }

        dataVos15.setUserId(2L);
        dataVos15.setDate(date);
        dataVos15.setVolExtract(volExtract);
        dataVos15.setVolCiti(volCiti);
        dataVos15.setCleanWaterSupply(cleanWaterSupply);
        dataVos15.setPressureCity(pressureCity);
        dataVos15.setDeltaCleanWaterSupply(dataVos15.getCleanWaterSupply() - lostCleanWaterSupply);
        return dataVos15;
    }


    /**
     * Проверка двух моментов вермени на принадлежностьк одному часу
     *
     * @param date        - дата1
     * @param dateToCheck - дата2
     * @return - true, если обе даты находятся в одном астрономическом часе
     * (23:00 и 23:59 дадут true,  23:00 и 22:59 - дадут false
     */
    private boolean isHourDateInHourNow(LocalDateTime date, LocalDateTime dateToCheck) {
        return (date == dateToCheck);

    }
    @Transactional
    public boolean updateDataVos15(DataVos15 dataVos15) {
        try {
            dataRep15.saveAndFlush(dataVos15);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DataVos15 getDataVosById(long id) {
            return dataRep15.findById(id).orElse(null);
    }

    public DataVos15 createDataVos15(Long id, Long userId, LocalDateTime date,
                                   Double volExtract, Double volCiti,
                                   Double cleanWaterSupply, Double deltaCleanWaterSupply,
                                   Double pressureCity) {
        DataVos15 prevData = dataRep15.getPrevData(date);
        if (prevData != null) deltaCleanWaterSupply = cleanWaterSupply - prevData.getCleanWaterSupply();


        return new DataVos15(id, userId, date,
                volExtract, volCiti,
                cleanWaterSupply, deltaCleanWaterSupply,
                pressureCity);
    }
}
