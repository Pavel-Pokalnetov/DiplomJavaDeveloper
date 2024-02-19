package ru.slenergo.AppMonitoring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private LocalDateTime date;
    @Column
    private Double volExtract;
    @Column
    private Double volCiti;
    @Column
    private Double cleanWaterSupply;
    @Column
    private Double deltaCleanWaterSupply;

}
