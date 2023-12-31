package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserValidator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@Primary
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserValidator userValidator;

    @Override
    public User createNewUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert;
        if (userValidator.validateUser(user)) {
            if (Objects.isNull(user.getName()) || user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("user")
                    .usingGeneratedKeyColumns("user_id");
            user.setId(simpleJdbcInsert.executeAndReturnKey(toMap(user)).intValue());
        } else {
            throw new ValidationException("Пользователь не прошёл валидацию");
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (userValidator.validateUser(user)) {
            findUserById(user.getId());
            jdbcTemplate.update("update user set login = ?, name = ?, email = ?, birthday = ? where user_id = ?",
                    user.getLogin(), user.getName(), user.getEmail(), user.getBirthday(), user.getId());
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query("select * from user", (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public User findUserById(int id) {
        User user;
        try {
            user = jdbcTemplate.queryForObject("SELECT* FROM user WHERE user_id =?", (rs, rowNum) -> makeUser(rs), id);
        } catch (RuntimeException e) {
            throw new UserNotFoundException(String.format("Пользователя с таким id: %s не существует.", id));
        }
        return user;
    }

    @Override
    public String addFriend(int userId, int friendId) {
        jdbcTemplate.update("insert into friend(user_id, follower_id) values(?, ?)", userId, friendId);
        return String.format("Пользователь с id %s добавлен в друзья к пользователю с id %s!", friendId, userId);
    }

    @Override
    public String removeFriend(int userId, int friendId) {
        jdbcTemplate.update("delete from friend WHERE user_id = ? AND follower_id = ?", userId, friendId);
        return String.format("Пользователь с id %s удален из друзей у пользователя с id %s!", friendId, userId);
    }

    @Override
    public Map<Integer, User> getMapUsers() {
        return null;
    }

    private List<Integer> idFriends(int id) {
        return jdbcTemplate.query("SELECT follower_id FROM friend WHERE user_id = ?", (rs, rowNum) ->
                rs.getInt("follower_id"), id);
    }

    private User makeUser(ResultSet rs) throws SQLException {
        User user = User.builder().id(rs.getInt("user_id")).login(rs.getString("login"))
                .name(rs.getString("name")).email(rs.getString("email"))
                .birthday(rs.getDate("birthday").toLocalDate()).build();
        user.getIdFriends().addAll(idFriends(user.getId()));
        return user;
    }

    private Map<String, Object> toMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("login", user.getLogin());
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("birthday", user.getBirthday());
        return values;
    }
}
