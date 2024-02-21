package ru.slenergo.AppMonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.slenergo.AppMonitoring.model.DataVos15;

import java.time.LocalDateTime;
import java.util.List;

public interface DataRepositoryVos15 extends JpaRepository<DataVos15, Long> {
    /**
     * получить последнюю запись
     */
    @Query("SELECT d FROM DataVos15  d ORDER BY d.date DESC LIMIT 1")
    DataVos15 findLastItem();

    /**
     * получить все записи за последние 24 часа
     */
    @Query("SELECT d FROM DataVos15  d ORDER BY d.date LIMIT 24")
    List<DataVos15> findLast24Hours();

    //todo добавить метод получения только записей за текущий день

    /**
     * получить предыдущую по времени запись
     */
    @Query("SELECT d FROM DataVos15 d where d.date< :date ORDER BY d.date DESC LIMIT 1")
    DataVos15 getPrevData(@Param("date") LocalDateTime date);

    /**
     * получить cледующую по времени запись
     */
    @Query("SELECT d FROM DataVos15 d where d.date> :date ORDER BY d.date ASC LIMIT 1")
    DataVos15 getNextData(@Param("date") LocalDateTime date);


    /**
     * Проверка наличия записи с заданной датой
     *
     * @param date - дата LocalDateTime
     * @return true-есть запись
     * false - нет записи
     */
    @Query("select case when count (d)>0 then true else false end from DataVos15 d where d.date=:date")
    boolean existsByDate(@Param("date") LocalDateTime date);

    DataVos15 getDataVos15ByDate(LocalDateTime date);
}
