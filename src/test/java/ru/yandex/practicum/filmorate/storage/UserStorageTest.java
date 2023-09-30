package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserStorageTest {

    private final UserStorage userStorage;

    @Test
    public void testFindUserById() {
        User newUser = User.builder().name("friend adipisicing").login("friend").email("friend@mail.ru")
                .birthday(LocalDate.parse("1976-08-20")).build();
        userStorage.createNewUser(newUser);

        Optional<User> userOptional = Optional.of(userStorage.findUserById(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testGetAllUser() {
        User newUser = User.builder().name("friend adipisicing").login("friend").email("friend@mail.ru")
                .birthday(LocalDate.parse("1976-08-20")).build();
        userStorage.createNewUser(newUser);

        List<User> users = userStorage.getUsers();

        assertNotNull(users, "Список пустой");
        assertEquals(2, users.size(), "Длина списка пользователей не совпадает с ожидаемым: 2");
    }

    @Test
    public void testUpdateUser() {
        User newUser = User.builder().name("friend adipisicing").login("friend").email("friend@mail.ru")
                .birthday(LocalDate.parse("1976-08-20")).build();
        userStorage.createNewUser(newUser);

        User newUser1 = User.builder().id(1).name("est adipisicing").login("doloreUpdate").email("mail@yandex.ru")
                .birthday(LocalDate.parse("1976-09-20")).build();

        userStorage.updateUser(newUser1);

        Optional<User> userOptional = Optional.of(userStorage.findUserById(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
        assertEquals(newUser1.getName(), userOptional.get().getName(), "Имена не совпадают.");
        assertEquals(newUser1.getLogin(), userOptional.get().getLogin(), "Логин не совпадает");
        assertEquals(newUser1.getEmail(), userOptional.get().getEmail(), "email не совпадает.");
    }

    @Test
    public void testCreateUser() {
        User newUser = User.builder().name("friend adipisicing").login("friend").email("friend@mail.ru")
                .birthday(LocalDate.parse("1976-08-20")).build();

        Optional<User> userOptional = Optional.of(userStorage.createNewUser(newUser));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 3)
                );
        assertEquals(newUser.getName(), userOptional.get().getName(), "Имена не совпадают.");
        assertEquals(newUser.getLogin(), userOptional.get().getLogin(), "Логин не совпадает");
        assertEquals(newUser.getEmail(), userOptional.get().getEmail(), "email не совпадает.");
    }
}
