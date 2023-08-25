package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Service
public class UserService {
    @Autowired UserValidator userValidator;

    public User getUserById(int id) {
        return new User("evgeny", "Evgeny", 65, "tea985@yandex.ru", LocalDate.of(1985,7,9));
    }

    public boolean createUser(User user) {
        return userValidator.validateUser(user);
    }
}
