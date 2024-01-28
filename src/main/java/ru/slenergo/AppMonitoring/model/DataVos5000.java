package ru.slenergo.AppMonitoring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "datavos5")
public class DataVos5000 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private final Long station_id = 1L;
    @Column
    private Long user_id;
    @Column
    private LocalDateTime date;
    @Column
    private Double volExtr;
    @Column
    private Double volCiti;
    @Column
    private Double volBackCity;
    @Column
    private Double volBackVos15;
    @Column
    private Double cleanWaterSupply;
    @Column
    private Double pressureCity;
    @Column
    private Double pressureBackCity;
    @Column
    private Double pressureBackVos15;

    public DataVos5000(Double volExtr,
                       Double volCiti,
                       Double volBackCity,
                       Double volBackVos15,
                       Double cleanWaterSupply,
                       Double pressureCity,
                       Double pressureBackCity,
                       Double pressureBackVos15) {
        this.volExtr = volExtr;
        this.volCiti = volCiti;
        this.volBackCity = volBackCity;
        this.volBackVos15 = volBackVos15;
        this.cleanWaterSupply = cleanWaterSupply;
        this.pressureCity = pressureCity;
        this.pressureBackCity = pressureBackCity;
        this.pressureBackVos15 = pressureBackVos15;
    }

}
