package ru.slenergo.AppMonitoring.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="reports")
public class Report {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long station_id;
    @Column
    private LocalDateTime date;
    @Column
    private TypeReports type;
    @Column
    private String url;


}
