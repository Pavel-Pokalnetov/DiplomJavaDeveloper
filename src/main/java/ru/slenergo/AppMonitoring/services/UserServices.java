package ru.slenergo.AppMonitoring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.slenergo.AppMonitoring.repository.UserRepository;

@Service
public class UserServices {
    @Autowired
    UserRepository userRepo;
}
