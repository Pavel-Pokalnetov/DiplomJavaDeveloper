package ru.slenergo.AppMonitoring.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.slenergo.AppMonitoring.model.DataVos5;

import java.util.List;

public interface DataRepositoryVos5 extends JpaRepository<DataVos5,Long> {
    @Query("SELECT d FROM DataVos5  d ORDER BY d.date LIMIT 1")
    DataVos5 findLastItem();

    @Query("SELECT d FROM DataVos5  d ORDER BY d.date LIMIT 31")
    List<DataVos5> findLastMont();
}
