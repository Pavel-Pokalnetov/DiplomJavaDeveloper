package ru.slenergo.AppMonitoring.model;


import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Data
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
    @Column(name = "group_id")
    private Long groupId;
}
