package ru.slenergo.AppMonitoring.model.services;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.slenergo.AppMonitoring.etc.exceptions.PrematureEntryException;
import ru.slenergo.AppMonitoring.model.entity.DataVos15;
import ru.slenergo.AppMonitoring.model.repository.DataRepositoryVos15;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DataServiceVOS15 {

    @Autowired
    DataRepositoryVos15 dataRep15;
    @Autowired
    ReportService reportService;

    /**
     * Получить данные за текущий день
     */
    public List<DataVos15> getCurrentDayVos15() {
        return dataRep15.findDataVos15sByDateIsAfterOrderByDateAsc(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
    }

    /**
     * Запись в базу данных
     */
    @Transactional
    public boolean saveDataVos15(DataVos15 dataVos15) {
        try {
            dataRep15.saveAndFlush(dataVos15);
            updateNextDataCleanWaterSupply(dataVos15);
            reportService.recalcSummaryReportByDate(dataVos15.getDate());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Обновление поля роста запаса чистой воды в следующей по времени записи
     *
     * @param dataVos15 - текущая запись
     */
    private void updateNextDataCleanWaterSupply(DataVos15 dataVos15) {
        DataVos15 nextData = dataRep15.getNextData(dataVos15.getDate());
        if (nextData != null) {
            nextData.setDeltaCleanWaterSupply(nextData.getCleanWaterSupply() - dataVos15.getCleanWaterSupply());
            dataRep15.saveAndFlush(nextData);
        }
    }



    /**
     * Создание записи по данным из формы с проверкой на повторный ввод
     *
     * @param date             - дата
     * @param volExtract       - объем добычи
     * @param volCiti          - отдача в город
     * @param cleanWaterSupply - запас чистой воды
     * @param pressureCity     - давление в трубопроводе в город
     * @return DataVos15
     * @throws PrematureEntryException
     */
    public DataVos15 createDataVos15(LocalDateTime date, Double volExtract, Double volLeftCity, Double volRightCity,
                                     Double cleanWaterSupply, Double pressureCity)
            throws PrematureEntryException {
        date = date.truncatedTo(ChronoUnit.HOURS); //усечение времени до часа (отбрасываем все что меньше часа)
        /* проверка на повторное добавление записи в текущем часе*/
        if (dataRep15.existsByDate(date)) {
            throw new PrematureEntryException(date);
        }
        DataVos15 dataVos15 = new DataVos15();
        return dataVos15.update(
                dataVos15.getId(),
                2L,
                date,
                volExtract,
                volLeftCity,
                volRightCity,
                cleanWaterSupply,
                pressureCity);
    }


    public boolean updateAndSaveDataVos15ByDate(DataVos15 dataVos15) {
        try {
            dataRep15.saveAndFlush(dataVos15);
            updateNextDataCleanWaterSupply(dataVos15);
            reportService.recalcSummaryReportByDate(dataVos15.getDate());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DataVos15 getDataVos15ById(long id) {
        return dataRep15.findById(id).orElse(null);
    }


    /**
     * Создание строки записи (без записи в базу)
     *
     * @param id               - id
     * @param userId           - идентификатор пользователя
     * @param date             - дата
     * @param volExtract       - объем добычи воды
     * @param volCiti          - объем подачи в город
     * @param cleanWaterSupply - запас чистой воды
     * @param pressureCity     - давление в трубопроводе
     * @return - dataVos15
     */
    public DataVos15 createDataVos15(Long id, Long userId, LocalDateTime date,
                                     Double volExtract, Double volLeftCiti, Double volRightCity,
                                     Double cleanWaterSupply,
                                     Double pressureCity) {

        DataVos15 prevData = dataRep15.getPrevData(date);
        Double deltaCleanWaterSupply;
        if (prevData != null) {
            deltaCleanWaterSupply = cleanWaterSupply - prevData.getCleanWaterSupply();
        } else {
            deltaCleanWaterSupply = cleanWaterSupply;
        }

        return new DataVos15(id, userId, date,
                volExtract, volLeftCiti, volRightCity,
                cleanWaterSupply, deltaCleanWaterSupply,
                pressureCity);
    }

    public List<DataVos15> getDataVos15ByDay(LocalDateTime date) {
        return dataRep15.findDataVos15sByDateBetweenOrderByDateAsc(date,date.plusHours(23));
    }
}
