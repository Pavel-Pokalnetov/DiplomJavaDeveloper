package ru.slenergo.AppMonitoring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static ru.slenergo.AppMonitoring.configuration.Config.formatterTimeOnly;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dataSummary")
public class DataSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private LocalDateTime date;
    @Column
    private Double volExtract;
    @Column
    private Double volCiti;
    @Column
    private Double cleanWaterSupply;
    @Column
    private Double deltaCleanWaterSupply;
    public String getDateT(){
        return date.format(formatterTimeOnly);
    }
}
