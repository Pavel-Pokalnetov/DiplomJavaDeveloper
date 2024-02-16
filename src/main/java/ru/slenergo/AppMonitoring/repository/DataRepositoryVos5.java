package ru.slenergo.AppMonitoring.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.slenergo.AppMonitoring.model.DataVos5;

import java.time.LocalDateTime;
import java.util.List;

public interface DataRepositoryVos5 extends JpaRepository<DataVos5, Long> {
    /** получить последнюю запись
     * @return
     */
    @Query("SELECT d FROM DataVos5  d ORDER BY d.date DESC LIMIT 1")
    DataVos5 findLastItem();

    /** получить все записи за последние 24 часа
     * @return
     */
    @Query("SELECT d FROM DataVos5  d ORDER BY d.date LIMIT 24")
    List<DataVos5> findLastDay();


    /**
     *получить предыдущую по времени запись
     */
    @Query("SELECT d FROM DataVos5 d where d.date< :date ORDER BY d.date DESC LIMIT 1")
    DataVos5 getPrevData(@Param("date") LocalDateTime date);
}
