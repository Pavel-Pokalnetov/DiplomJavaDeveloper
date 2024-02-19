package ru.slenergo.AppMonitoring.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @Column(name = "user_id")
    private Long userId;
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
    @Column
    private Double pressureCity;


    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm");

    public String getDateT() {
        return date.format(formatter);
    }
}
