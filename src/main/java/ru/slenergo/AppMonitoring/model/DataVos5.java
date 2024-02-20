package ru.slenergo.AppMonitoring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.slenergo.AppMonitoring.configuration.Config.formatter;

@Entity
@Data
@Getter
@Setter
@Table(name = "datavos5")
@AllArgsConstructor
@NoArgsConstructor
public class DataVos5 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userId;
    @Column(unique = true)
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

    public Double getDeltaCleanWaterSupplyCalculated(){
        return getVolAll()-volCiti;
    }
    public Double getVolAll() {
        return volExtract + volBackCity + volBackVos15;
    }

    public String getDateT(){
        return date.format(formatter);
    }
}
