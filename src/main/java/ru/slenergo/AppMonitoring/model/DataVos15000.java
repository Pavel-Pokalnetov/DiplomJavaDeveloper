package ru.slenergo.AppMonitoring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "datavos15")
public class DataVos15000 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private final Long station_id = 2L;
    @Column
    private Long user_id;
    @Column
    private LocalDateTime date;
    @Column
    private Double volExtr;
    @Column
    private Double volCiti;
    @Column
    private Double cleanWaterSupply;


    public DataVos15000(Double volExtr, Double volCiti, Double cleanWaterSupply) {
        this.volExtr = volExtr;
        this.volCiti = volCiti;
        this.cleanWaterSupply = cleanWaterSupply;
    }

}
