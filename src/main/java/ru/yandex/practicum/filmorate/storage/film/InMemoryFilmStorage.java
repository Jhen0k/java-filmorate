package ru.yandex.practicum.filmorate.storage.film;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IncorrectValueException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.enums.Genres;
import ru.yandex.practicum.filmorate.model.enums.Rating;
import ru.yandex.practicum.filmorate.service.FilmValidator;
import ru.yandex.practicum.filmorate.service.GeneratorId;

import java.util.*;


@Component
@Slf4j
@Builder
public class InMemoryFilmStorage implements FilmStorage {

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
    public List<Mpa> getAllMpa() {
        List<Mpa> mpa = new ArrayList<>();
        List<Rating> ratings = Arrays.asList(Rating.values());
        for (int i = 0; i < ratings.size(); i++) {
            mpa.add(new Mpa(i + 1, ratings.get(i).toString()));
        }
        return mpa;
    }

    @Override
    public Mpa getMpa(int id) {
        List<Rating> ratings = Arrays.asList(Rating.values());
        return new Mpa(id, ratings.get(id -1).toString());
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        List<Genres> genresList = Arrays.asList(Genres.values());
        for (int i = 0; i < genresList.size(); i++) {
            genres.add(new Genre(i + 1, genresList.get(i).toString()));
        }
        return genres;
    }

    @Override
    public Genre getGenre(int id) {
        List<Genres> genresList = Arrays.asList(Genres.values());
        return new Genre(id, genresList.get(id).toString());
    }
}
