package ru.slenergo.AppMonitoring.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.slenergo.AppMonitoring.model.DataVos5;

import java.time.LocalDateTime;
import java.util.List;

public interface DataRepositoryVos5 extends JpaRepository<DataVos5, Long> {
    /**
     * получить последнюю запись
     */
    @Query("SELECT d FROM DataVos5  d ORDER BY d.date DESC LIMIT 1")
    DataVos5 findLastItem();

    /**
     * получить все записи за последние 24 часа
     */
    @Query("SELECT d FROM DataVos5  d ORDER BY d.date LIMIT 24")
    List<DataVos5> findLast24Hours();

    /**
     * получить предыдущую по времени запись
     */
    @Query("SELECT d FROM DataVos5 d where d.date< :date ORDER BY d.date DESC LIMIT 1")
    DataVos5 getPrevData(@Param("date") LocalDateTime date);

    /**
     * получить cледующую по времени запись
     */
    @Query("SELECT d FROM DataVos5 d where d.date> :date ORDER BY d.date ASC LIMIT 1")
    DataVos5 getNextData(@Param("date") LocalDateTime date);


    /**
     * Проверка наличия записи с заданной датой
     *
     * @param date - дата LocalDateTime
     * @return true-есть запись
     * false - нет записи
     */
    @Query("select case when count (d)>0 then true else false end from DataVos5 d where d.date=:date")
    boolean existsByDate(@Param("date") LocalDateTime date);

    DataVos5 getDataVos5ByDate(LocalDateTime date);
}
