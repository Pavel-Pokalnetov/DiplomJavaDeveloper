package ru.slenergo.AppMonitoring.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usergroups")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
}
