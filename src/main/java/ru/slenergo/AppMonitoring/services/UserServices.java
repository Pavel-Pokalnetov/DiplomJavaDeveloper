package ru.slenergo.AppMonitoring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.slenergo.AppMonitoring.model.User;
import ru.slenergo.AppMonitoring.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServices {
    @Autowired
    UserRepository userRepo;

    /** Получить список всех пользователей
     * @return
     */
    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();

        userRepo.findAll().iterator().forEachRemaining(userList::add);
        return userList;
    }

    public User getUserById(Long id){
        return userRepo.findById(id).orElse(null);
    }
}
