package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
public class ControllerFullTest {
    private final String http = "http://localhost:8080/users";

    @Autowired private TestRestTemplate testRestTemplate;

    @Test
    public void findUserById_shouldReturnUser() {

    }
}
