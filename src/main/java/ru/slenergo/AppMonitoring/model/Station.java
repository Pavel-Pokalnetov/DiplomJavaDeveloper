package ru.slenergo.AppMonitoring.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
@Table(name = "stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
}
