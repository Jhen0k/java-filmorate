package ru.yandex.practicum.filmorate.storage.film;

import jdk.jfr.Frequency;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IncorrectValueException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmValidator;
import ru.yandex.practicum.filmorate.service.GeneratorId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage{

    private final GeneratorId generatorId = new GeneratorId();
    private final FilmValidator filmValidator;
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Film create(Film film) {
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

    @Override
    public Film update(Film film) {
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

    @Override
    public List<Film> getFilms() {
        List<Film> films1 = new ArrayList<>(films.values());
        log.info("Текущее количество постов: {}", films.size());
        return films1;
    }

    @Override
    public Film getFilm(int id) {
        if (films.containsKey(id)) {
            log.info(films.get(id).toString());
            return films.get(id);
        } else {
            throw new IncorrectValueException(id);
        }
    }

    @Override
    public Map<Integer, Film> getMapFilms() {
        return films;
    }
}
