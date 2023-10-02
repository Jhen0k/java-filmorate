package ru.yandex.practicum.filmorate.storage.user;

import lombok.Builder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IncorrectValueException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.GeneratorId;
import ru.yandex.practicum.filmorate.service.UserValidator;

import java.util.*;

@Component
@Builder
public class InMemoryUserStorage implements UserStorage {

    private final GeneratorId generatorId;
    private final UserValidator userValidator;
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public User createNewUser(User user) {
        if (userValidator.validateUser(user)) {
            if (Objects.isNull(user.getName()) || user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            int id = generatorId.getNextFreeId();
            user.setId(id);
            users.put(id, user);
        } else {
            throw new ValidationException("Пользователь не прошёл валидацию");
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (userValidator.validateUser(user)) {
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
            } else {
                throw new IncorrectValueException(user.getId());
            }
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findUserById(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new UserNotFoundException(String.format("Пользователя с таким id: %s не существует.", id));
        }
    }

    @Override
    public String addFriend(int userId, int friendId) {
        findUserById(userId).getIdFriends().add(friendId);
        findUserById(friendId).getIdFriends().add(userId);
        return String.format("Пользователь с id %s добавлен в друзья к пользователю с id %s!", friendId, userId);
    }

    @Override
    public String removeFriend(int userId, int friendId) {
        findUserById(userId).getIdFriends().remove(friendId);
        return String.format("Пользователь с id %s удален из друзей у пользователя с id %s!", friendId, userId);
    }

    @Override
    public Map<Integer, User> getMapUsers() {
        return users;
    }
}
