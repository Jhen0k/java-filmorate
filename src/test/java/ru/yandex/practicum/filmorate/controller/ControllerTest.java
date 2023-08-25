package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired MockMvc mockMvc;

    @Test
    public void findUserById_shouldReturnUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/users/user?id=0"))
                .andExpect(status().isOk()).andExpect(content().string("{\"login\":\"evgeny\"" +
                        ",\"name\":\"Evgeny\",\"id\":65,\"email\":\"tea985@yandex.ru\",\"birthday\":\"09-07-1985\"}"));
    }
}
