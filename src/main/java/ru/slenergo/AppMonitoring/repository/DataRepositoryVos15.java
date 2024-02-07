package ru.slenergo.AppMonitoring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.slenergo.AppMonitoring.model.DataVos15;
import ru.slenergo.AppMonitoring.model.DataVos5;

public interface DataRepositoryVos15 extends CrudRepository<DataVos15,Long> {
    @Query("SELECT d FROM DataVos15  d ORDER BY d.date LIMIT 1")
    DataVos5 findLastItem();
}
