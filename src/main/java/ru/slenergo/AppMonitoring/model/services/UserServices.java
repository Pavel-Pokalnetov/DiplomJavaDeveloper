package ru.slenergo.AppMonitoring.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.slenergo.AppMonitoring.model.entity.User;
import ru.slenergo.AppMonitoring.model.repository.UserRepository;

import java.util.List;

/**
 * Сервис работы с пользователями
 */
@Service
public class UserServices {
    @Autowired
    UserRepository userRepo;

    /** Получить список всех пользователей
     * @return
     */
    public List<User> getUsers() {
        return userRepo.findUsersByIdNotNull();
    }

    /** Получить пользователя по его id
     * @param id
     * @return
     */
    public User getUserById(Long id){
        return userRepo.getUserById(id);
    }
}
