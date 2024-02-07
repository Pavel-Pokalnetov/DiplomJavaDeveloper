package ru.slenergo.AppMonitoring.model;


import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="reports")
public class Report {

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
