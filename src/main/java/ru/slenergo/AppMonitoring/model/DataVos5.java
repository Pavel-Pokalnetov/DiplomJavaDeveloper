package ru.slenergo.AppMonitoring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static ru.slenergo.AppMonitoring.configuration.Config.formatterDateTimeFull;
import static ru.slenergo.AppMonitoring.configuration.Config.formatterTimeOnly;

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
    @Column(unique = true,nullable = false)
    private LocalDateTime date;
    @Column
    private Double volExtract;
    @Column
    private Double volCity;
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
        return getVolAll()- volCity;
    }
    public Double getVolAll() {
        return volExtract + volBackCity + volBackVos15;
    }

    public String getDateT(){
        return date.format(formatterTimeOnly);
    }
}
