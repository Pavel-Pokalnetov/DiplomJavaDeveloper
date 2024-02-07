package ru.slenergo.AppMonitoring.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.slenergo.AppMonitoring.model.DataVos5;

import java.util.List;

public interface DataRepositoryVos5 extends JpaRepository<DataVos5, Long> {
    @Query("SELECT d FROM DataVos5  d ORDER BY d.date DESC LIMIT 1")
    DataVos5 findLastItem();

    @Query("SELECT d FROM DataVos5  d ORDER BY d.date LIMIT 24")
    List<DataVos5> findLastDay();



/*
SQL
получить предыдущую по времени запись
8 февраля в 02:05
Вы можете использовать подзапрос для получения предыдущей по времени записи. Вот пример:

SELECT * 
FROM your_table t1
WHERE t1.date < (SELECT MAX(t2.date) FROM your_table t2 WHERE t2.date < t1.date)
ORDER BY t1.date DESC
LIMIT 1;

В этом запросе мы сначала выбираем все записи из таблицы "your_table",
затем фильтруем их, чтобы оставить только те, у которых дата меньше,
чем максимальная дата среди записей с более ранней датой. Затем мы сортируем оставшиеся
записи в обратном порядке по дате и выбираем только одну запись.
*/

}
