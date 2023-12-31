package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import javax.validation.Valid;
import java.util.List;

public interface FilmStorage {

    Film create(@Valid @RequestBody Film film);

    Film update(@Valid @RequestBody Film film);

    List<Film> getFilms();

    Film getFilm(int id);

    List<Mpa> getAllMpa();

    Mpa getMpa(int id);

    List<Genre> getAllGenres();

    Genre getGenre(int id);
}
