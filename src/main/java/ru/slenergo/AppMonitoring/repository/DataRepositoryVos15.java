package ru.slenergo.AppMonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.slenergo.AppMonitoring.model.DataVos15;
import ru.slenergo.AppMonitoring.model.DataVos5;

import java.time.LocalDateTime;
import java.util.List;

public interface DataRepositoryVos15 extends JpaRepository<DataVos15, Long> {
    /**
     * Получить запись предыдущую относительно заданного времени
     */
    @Query("SELECT d FROM DataVos15 d where d.date<:date ORDER BY d.date DESC LIMIT 1")
    DataVos15 getPrevData(@Param("date") LocalDateTime date);

    /**
     * Получить запись cледующую относительно заданного времени
     */
    @Query("SELECT d FROM DataVos15 d where d.date>:date ORDER BY d.date ASC LIMIT 1")
    DataVos15 getNextData(@Param("date") LocalDateTime date);

    /**
     * Проверка наличия записи с заданной датой
     *
     * @param date - дата LocalDateTime
     * @return true-есть запись
     * false - нет записи
     */
    boolean existsByDate(LocalDateTime date);

    /** Получить запись по дате
     * @param date
     * @return
     */
    DataVos15 getDataVos15ByDate(LocalDateTime date);

    /**
     *  Получить все записи начиная с указанной даты сортировка по возрастанию даты
     * @param date заданная дата и время LocalDateTime
     * @return список DataVos15
     */
    List<DataVos15> findDataVos15sByDateIsAfterOrderByDateAsc(LocalDateTime date);

    /**
     * Получить предыдущий запас воды
     */
    @Query("select d.cleanWaterSupply from DataVos15 d where d.date<:date order by d.date desc limit 1")
    Double getPrevCleanWaterSupplyByDate(@Param("date") LocalDateTime date);

    /**
     * Получить следующий запас воды
     */
    @Query("select d.cleanWaterSupply from DataVos15 d where d.date>:date order by d.date asc limit 1")
    Double getNextCleanWaterSupplyByDate(@Param("date") LocalDateTime date);

    List<DataVos15> findDataVos15sByDateBetweenOrderByDateAsc(LocalDateTime date, LocalDateTime date2);
}