package ru.yandex.practicum.filmorate.controller;

import org.springframework.stereotype.Component;

@Component
public class GeneratorId {
    private int nextFreeId = 1;

    public int getNextFreeId() {
        return nextFreeId++;
    }
}
