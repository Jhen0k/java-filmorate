package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NotNull
public class User {
    @NotBlank
    private final String login;
    private String name;
    private int id;
    @Email
    private final String email;
    private final LocalDate birthday;
}
