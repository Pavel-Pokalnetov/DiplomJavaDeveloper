package ru.slenergo.AppMonitoring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.slenergo.AppMonitoring.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    Optional<User> findById(Long aLong);
}
