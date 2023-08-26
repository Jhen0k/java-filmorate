package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NotNull
public class Film {
    private int id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;
}
