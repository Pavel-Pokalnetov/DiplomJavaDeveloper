package ru.slenergo.AppMonitoring.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "datavos5")
public class DataVos5 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long station_id;
    @Column
    private Long user_id;
    @Column
    private LocalDateTime date;
    @Column
    private Double volExtract;
    @Column
    private Double volCiti;
    @Column
    private Double volBackCity;
    @Column
    private Double volBackVos15;
    @Column
    private Double cleanWaterSupply;
    @Column
    private Double deltaCleanWaterSupply;
    @Column
    private Double pressureCity;
    @Column
    private Double pressureBackCity;
    @Column
    private Double pressureBackVos15;

    public DataVos5(Double volExtract, Double volBackCity, Double volBackVos15, Double cleanWaterSupply, Double pressureCity, Double pressureBackCity, Double pressureBackVos15) {

        this.volExtract = volExtract;
        this.volBackCity = volBackCity;
        this.volBackVos15 = volBackVos15;
        this.cleanWaterSupply = cleanWaterSupply;
        this.pressureCity = pressureCity;
        this.pressureBackCity = pressureBackCity;
        this.pressureBackVos15 = pressureBackVos15;
    }

    public DataVos5() {
    }

    public Double getVolAll() {
        return volExtract + volBackCity + volBackVos15;
    }


}
