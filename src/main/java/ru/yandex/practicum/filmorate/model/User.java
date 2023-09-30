package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.enums.Status;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NotNull
@ApiModel(description = "Пользователь")
public class User {

    private int id;

    @NotBlank
    private final String login;

    private String name;

    @Email
    @ApiModelProperty(value = "емайл", example = "tea985@yandex.ru")
    private String email;

    @ApiModelProperty(value = "Дата в формате:", example = "1978-07-19")
    private final LocalDate birthday;

    @JsonIgnore
    private final Set<Integer> idFriends = new HashSet<>();

    @JsonIgnore
    private Status status;

}
