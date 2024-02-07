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



//    @Modifying
//    @Query("UPDATE DataVos5 e " +
//            "SET e.cleanWaterSupply = :cleanWaterSupply," +
//            " e.date = :date," +
//            " e.deltaCleanWaterSupply = :deltaCleanWaterSupply," +
//            " e.pressureBackCity = :pressureBackCity," +
//            " e.pressureBackVos15 = :pressureBackVos15," +
//            " e.pressureCity = :pressureCity," +
//            " e.stationId = :stationId," +
//            " e.userId = :userId," +
//            " e.volBackCity = :volBackCity," +
//            " e.volBackVos15 = :volBackVos15," +
//            " e.volCiti = :volCiti," +
//            " e.volExtract = :volExtract " +
//            "WHERE e.id = :id")
//    void update( DataVos5 data);

}
