package ru.slenergo.AppMonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import ru.slenergo.AppMonitoring.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    User getUserById(Long id);
    List<User> findUsersByIdNotNull();

}
