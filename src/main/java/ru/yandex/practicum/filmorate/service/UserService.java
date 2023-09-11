package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public String addFriend(int userId, int friendId) {
        findId(userId, friendId);
        userStorage.getMapUsers().get(userId).getIdFriends().add(friendId);
        userStorage.getMapUsers().get(friendId).getIdFriends().add(userId);
        log.info(String.format("Добавление друга c id: %s к пользователю c id: %s в друзья.", friendId, userId));
        return String.format("Пользователь с id %s добавлен в друзья к пользователю с id %s!", friendId, userId);
    }

    public String removeFriend(int userId, int friendId) {
        findId(userId, friendId);
        userStorage.getMapUsers().get(userId).getIdFriends().remove(friendId);
        return String.format("Пользователь с id %s удален из друзей у пользователя с id %s!", friendId, userId);
    }

    public List<User> listOfMutualFriends(int userId, int friendId) {
        findId(userId, friendId);
        List<User> listMutualFriends = new ArrayList<>();

        for (Integer id : userStorage.findUserById(userId).getIdFriends()) {
            if (userStorage.findUserById(friendId).getIdFriends().contains(id)) {
                listMutualFriends.add(userStorage.findUserById(id));
            }
        }
        log.info("Текущее количество общих друзей: " + userStorage.getMapUsers().size());
        return listMutualFriends;
    }

    public List<User> listFriendsUser(int userId) {
        List<User> allFriends = new ArrayList<>();
        if (!userStorage.getMapUsers().containsKey(userId)) {
            throw new UserNotFoundException(String.format("Пользователя с таким id: %s не существует.", userId));
        }
        List<Integer> usersId = new ArrayList<>(userStorage.findUserById(userId).getIdFriends());
        for (Integer id : usersId) {
            allFriends.add(userStorage.findUserById(id));
        }
        log.info("Количество друзей: " + allFriends.size());
        return allFriends;
    }

    private void findId(int userId, int friendId) {
        if (!userStorage.getMapUsers().containsKey(userId)) {
            throw new UserNotFoundException(String.format("Пользователя с таким id: %s не существует.", userId));
        }
        if (!userStorage.getMapUsers().containsKey(friendId)) {
            throw new UserNotFoundException(String.format("Друга с таким id: %s не существует.", friendId));
        }
    }

}
