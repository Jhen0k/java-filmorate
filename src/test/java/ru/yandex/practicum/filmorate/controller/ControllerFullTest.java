package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import ru.yandex.practicum.filmorate.model.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
public class ControllerFullTest {

    @Autowired private TestRestTemplate testRestTemplate;

    @Test
    public void findUserById_shouldReturnUser() {
        User user = new User("evgeny", "Евгений", 65, "tea985@yandex.ru", "09-07-1985");
        User actualUser = testRestTemplate.getForObject("http://localhost:8080/users/user?id=0", User.class);

        assertNotNull(user, "Пользователь не создан!");
        assertNotNull(actualUser, "Пользователь не создан!");
        assertEquals(user, actualUser, "Пользователи не совпадают");

    }
}
