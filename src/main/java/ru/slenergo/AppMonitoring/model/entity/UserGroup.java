package ru.slenergo.AppMonitoring.model.entity;


import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "usergroups")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String role;
}
