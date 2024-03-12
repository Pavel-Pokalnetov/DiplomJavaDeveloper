package ru.slenergo.AppMonitoring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.slenergo.AppMonitoring.model.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    User getUserById(Long id);
    List<User> findUsersByIdNotNull();

}
