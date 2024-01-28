package ru.slenergo.AppMonitoring.model;

import jakarta.persistence.*;
import lombok.Data;

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
