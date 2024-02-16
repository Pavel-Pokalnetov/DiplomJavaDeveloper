package ru.slenergo.AppMonitoring.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "datavos15")
public class DataVos15 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Column
    private Double pressureCity;
}
