package ru.slenergo.AppMonitoring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.slenergo.AppMonitoring.model.User;

public interface UserRepository extends CrudRepository<User,Long> {
}
