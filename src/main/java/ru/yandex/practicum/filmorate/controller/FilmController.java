package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@AllArgsConstructor
@Slf4j
public class FilmController {
    private final GeneratorId generatorId = new GeneratorId();
    private final FilmValidator filmValidator = new FilmValidator();
    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        if (filmValidator.validatorFilm(film)) {
            int id = generatorId.getNextFreeId();
            log.info(film.toString());
            film.setId(id);
            films.put(id, film);
        } else {
            throw new ValidationException("Пользователь не прошёл валидацию");
        }
        return film;
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        if (filmValidator.validatorFilm(film)) {
            if (films.containsKey(film.getId())) {
                log.info(film.toString());
                films.put(film.getId(), film);
            } else {
                throw new ValidationException("id: " + film.getId() + " не существует.");
            }
        }
        return film;
    }

    @GetMapping()
    public List<Film> getFilms() {
        List<Film> films1 = new ArrayList<>(films.values());
        log.info("Текущее количество постов: {}", films.size());
        return films1;
    }

    @GetMapping("/film")
    public Film getFilm(int id) {
        if (films.containsKey(id)) {
            log.info(films.get(id).toString());
            return films.get(id);
        } else {
            throw new ValidationException("id: " + id + " не существует.");
        }
    }

}
