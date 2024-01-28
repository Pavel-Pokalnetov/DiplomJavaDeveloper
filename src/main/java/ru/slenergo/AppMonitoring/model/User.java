package ru.slenergo.AppMonitoring.model;


import lombok.Data;
import jakarta.persistence.*;
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private Long groupId;
    @Column
    private Long stationId;
}