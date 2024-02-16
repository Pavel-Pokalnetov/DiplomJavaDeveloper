package ru.slenergo.AppMonitoring.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.slenergo.AppMonitoring.model.DataVos5;
import ru.slenergo.AppMonitoring.repository.DataRepositoryVos15;
import ru.slenergo.AppMonitoring.repository.DataRepositoryVos5;
import ru.slenergo.AppMonitoring.services.exceptions.PrematureEntryException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    @Autowired
    DataRepositoryVos5 dataRep5;

    @Autowired
    DataRepositoryVos15 dataRep15;

    /**
     * Получить все данные для ВОС5
     *
     * @return
     */
    public List<DataVos5> getAllVos5() {
        List<DataVos5> dataVos5 = new ArrayList<>();
        dataRep5.findAll().iterator().forEachRemaining(dataVos5::add);
        return dataVos5;
    }

    /**
     * Получить данные для ВОС5 за последние 24 часа
     *
     * @return
     */
    public List<DataVos5> getLastDayVos5() {

        List<DataVos5> dataVos5 = new ArrayList<>();
        dataRep5.findLastDay().iterator().forEachRemaining(dataVos5::add);
        return dataVos5;
    }

    /**
     * Получить последнюю запись для ВОС5
     *
     * @return
     */
    public DataVos5 getLastDataItemVos5() {

        DataVos5 data = dataRep5.findLastItem();
        return data;
    }


    /**
     * Запись в базу данных для ВОС5000
     *
     * @param dataVos5
     * @return true - если успешно,
     */
    public boolean saveDataToDbVos5(DataVos5 dataVos5) {
        try {
            dataRep5.save(dataVos5);
            dataRep5.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Создание записи по данным из формы с проверкой на повторный ввод в текущем часе
     *
     * @param date
     * @param volExtract
     * @param volCiti
     * @param volBackCity
     * @param volBackVos15
     * @param cleanWaterSupply
     * @param pressureCity
     * @param pressureBackCity
     * @param pressureBackVos15
     * @return
     * @throws PrematureEntryException
     */
    public DataVos5 createDataVos5(LocalDateTime date, Double volExtract, Double volCiti,
                                   Double volBackCity, Double volBackVos15, Double cleanWaterSupply,
                                   Double pressureCity, Double pressureBackCity, Double pressureBackVos15)
            throws PrematureEntryException {
        DataVos5 dataVos5 = new DataVos5();
        DataVos5 dataLost = getLastDataItemVos5();
        Double lostCleanWaterSupply = 0.0;

        if (dataLost != null) {
            // если предыдущей записи нет
            lostCleanWaterSupply = dataLost.getCleanWaterSupply();
        }

        /*проверка на повторное добавление записи в текущем часе*/
        if (dataLost != null && isHourDateInHourNow(date, dataLost.getDate())) throw new PrematureEntryException(dataLost.getDate());

        dataVos5.setDate(date.truncatedTo(ChronoUnit.HOURS));
        dataVos5.setVolExtract(volExtract);
        dataVos5.setVolCiti(volCiti);
        dataVos5.setVolBackCity(volBackCity);
        dataVos5.setVolBackVos15(volBackVos15);
        dataVos5.setCleanWaterSupply(cleanWaterSupply);
        dataVos5.setPressureCity(pressureCity);
        dataVos5.setPressureBackCity(pressureBackCity);
        dataVos5.setPressureBackVos15(pressureBackVos15);

        dataVos5.setDeltaCleanWaterSupply(dataVos5.getCleanWaterSupply() - lostCleanWaterSupply);


        return dataVos5;
    }


    /**
     * Проверка совпадение времени (час) с текущим моментом
     * если во времени совпадает значение часа, то возвращает TRUE
     *
     * @param dateToCheck
     * @return
     */
    private boolean isHourDateInHourNow(LocalDateTime date, LocalDateTime dateToCheck) {
        int hour1 = date.toLocalTime().getHour();
        int hour2 = dateToCheck.toLocalTime().getHour();
        int day1 = date.toLocalDate().getDayOfMonth();
        int day2 = dateToCheck.toLocalDate().getDayOfMonth();
        return (hour1 == hour2) && (day1 == day2);
    }

    public boolean updateDataVos5(DataVos5 dataVos5) {
        try {
            dataRep5.saveAndFlush(dataVos5);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DataVos5 getDataVosById(long id) {
        if (dataRep5.existsById(id)) {
            return dataRep5.findById(id).orElse(null);
        }
        return null;
    }

    public DataVos5 createDataVos5(Long id, Long stationId, Long userId, LocalDateTime date,
                                   Double volExtract, Double volCiti,
                                   Double volBackCity, Double volBackVos15,
                                   Double cleanWaterSupply, Double deltaCleanWaterSupply,
                                   Double pressureCity, Double pressureBackCity, Double pressureBackVos15) {
        DataVos5 prevData = dataRep5.getPrevData(date);
        if (prevData != null) deltaCleanWaterSupply = cleanWaterSupply - prevData.getCleanWaterSupply();


        return new DataVos5(id, stationId, userId, date,
                volExtract, volCiti,
                volBackCity, volBackVos15,
                cleanWaterSupply, deltaCleanWaterSupply,
                pressureCity, pressureBackCity, pressureBackVos15);
    }
}
