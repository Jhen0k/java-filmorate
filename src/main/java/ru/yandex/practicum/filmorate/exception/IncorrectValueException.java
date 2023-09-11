package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;

@Getter
public class IncorrectValueException extends RuntimeException {

    private final Integer value;

    public IncorrectValueException(Integer value) {
        this.value = value;
    }
}
