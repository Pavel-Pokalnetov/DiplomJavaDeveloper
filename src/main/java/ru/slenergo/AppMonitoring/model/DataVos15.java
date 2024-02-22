package ru.slenergo.AppMonitoring.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

import static ru.slenergo.AppMonitoring.configuration.Config.formatterDateTimeFull;
import static ru.slenergo.AppMonitoring.configuration.Config.formatterTimeOnly;


/**
 * Запись часового расхода по станции ВОС15000
 *
 */
@Entity
@Data
@Getter
@Setter
@Table(name = "datavos15")
@AllArgsConstructor
@NoArgsConstructor
public class DataVos15 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userId;
    @Column
    private LocalDateTime date;
    @Column
    private Double volExtract;
    @Column
    private Double volCity;
    @Column
    private Double cleanWaterSupply;
    @Column
    private Double deltaCleanWaterSupply;
    @Column
    private Double pressureCity;

    public Double getDeltaCleanWaterSupplyCalculated(){
        return volExtract - volCity;
    }

    public String getDateT() {
        return date.format(formatterTimeOnly);
    }
}
