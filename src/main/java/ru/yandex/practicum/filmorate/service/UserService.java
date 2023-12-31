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
        log.info(String.format("Добавление друга c id: %s к пользователю c id: %s в друзья.", friendId, userId));
        return userStorage.addFriend(userId, friendId);
    }

    public String removeFriend(int userId, int friendId) {
        return userStorage.removeFriend(userId, friendId);
    }

    public List<User> listOfMutualFriends(int userId, int friendId) {
        List<User> listMutualFriends = new ArrayList<>();

        for (Integer id : userStorage.findUserById(userId).getIdFriends()) {
            if (userStorage.findUserById(friendId).getIdFriends().contains(id)) {
                listMutualFriends.add(userStorage.findUserById(id));
            }
        }
        log.info("Текущее количество общих друзей: " + userStorage.getUsers().size());
        return listMutualFriends;
    }

    public List<User> listFriendsUser(int userId) {
        List<User> allFriends = new ArrayList<>();
        try {
            List<Integer> usersId = new ArrayList<>(userStorage.findUserById(userId).getIdFriends());
            log.info(usersId.toString());
            for (Integer id : usersId) {
                allFriends.add(userStorage.findUserById(id));
            }
            log.info("Количество друзей: " + allFriends.size());
        } catch (RuntimeException e) {
            throw new UserNotFoundException(String.format("Пользователя с таким id: %s не существует.", userId));
        }
        return allFriends;
    }
}
