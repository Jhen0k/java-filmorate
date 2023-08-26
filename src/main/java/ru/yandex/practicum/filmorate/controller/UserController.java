package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final GeneratorId generatorId;
    private final UserService userService;
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        if (userService.createUser(user)) {
            if (Objects.isNull(user.getName())) {
                user.setName(user.getLogin());
            }
            int id = generatorId.getNextFreeId();
            log.info(user.toString());
            user.setId(id);
            users.put(id ,user);
        } else {
            throw new ValidationException("Пользователь не прошёл валидацию");
        }
        return user;
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) {
        if (userService.createUser(user)) {
            if (users.containsKey(user.getId())) {
                log.info(user.toString());
                users.put(user.getId(), user);
            } else {
                throw new ValidationException("id: " + user.getId() + " не существует.");
            }
        }
        return user;
    }

    @GetMapping()
    public List<User> getUser() {
        List<User> users1 = new ArrayList<>(users.values());
        log.info("Текущее количество постов: {}", users.size());
        return users1;
    }

    @GetMapping("/user")
    public User findUserById(int id) {
        if (users.containsKey(id)) {
            log.info(users.get(id).toString());
            return users.get(id);
        } else {
            throw new ValidationException("id: " + id + " не существует.");
        }
    }
}
