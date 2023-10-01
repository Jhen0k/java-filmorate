package ru.yandex.practicum.filmorate.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.service.UserValidator;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"UserController"})
public class UserController {

    private final UserStorage userStorage;
    private final UserService userService;
    private final UserValidator userValidator;

    @ApiOperation("Добавление нового пользователя")
    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        log.info(user.toString());
        return userStorage.createNewUser(user);
    }

    @ApiOperation("Обновление пользователя")
    @PutMapping()
    public User update(@Valid @RequestBody User user) {
        return userStorage.updateUser(user);
    }

    @ApiOperation("Добавление друга")
    @PutMapping("/{userId}/friends/{friendId}")
    public String addFriend(
            @PathVariable Integer userId,
            @PathVariable Integer friendId
    ) {
        userValidator.validateParameter(userId, friendId);
        return userService.addFriend(userId, friendId);
    }

    @ApiOperation("Удаление друга")
    @DeleteMapping("/{userId}/friends/{friendId}")
    public String remove(
            @PathVariable Integer userId,
            @PathVariable Integer friendId
    ) {
        userValidator.validateParameter(userId, friendId);
        return userService.removeFriend(userId, friendId);
    }

    @ApiOperation("Получение всех пользователей")
    @GetMapping()
    public List<User> getUser() {
        return userStorage.getUsers();
    }

    @ApiOperation("Получение пользователя по ID")
    @GetMapping("/{userId}")
    public User findUserById(@PathVariable Integer userId) {
        return userStorage.findUserById(userId);
    }

    @ApiOperation("Получение списка общих друзей.")
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> listOfMutualFriends(@PathVariable Integer id,
                                          @PathVariable Integer otherId
    ) {
        userValidator.validateParameter(id, otherId);
        return userService.listOfMutualFriends(id, otherId);
    }

    @ApiOperation("Получение списка пользователей, являющихся его друзьями")
    @GetMapping("/{id}/friends")
    public List<User> listAllFriendUser(@PathVariable Integer id) {
        if (id < 0) {
            throw new IncorrectParameterException("userId");
        }
        return userService.listFriendsUser(id);
    }
}
