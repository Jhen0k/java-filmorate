package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final FilmValidator filmValidator;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public String addLike(Integer id, Integer userId) {
        Film film = findId(id, userId);
        film.getLikes().add(userId);
        filmStorage.update(film);
        return String.format("Пользователь с id: %s поставил лайк фильму с id: %s ", userId, id);
    }

    public String removeLike(Integer id, Integer userId) {
        Film film = findId(id, userId);
        film.getLikes().remove(userId);
        filmStorage.update(film);
        return String.format("Пользователь с id: %s удалил лайк фильма с id: %s", userId, id);
    }

    public List<Film> getTenPopularFilms(Integer count) {

        return filmStorage.getFilms().stream().sorted((f0, f1) -> compare(f0.getRate(),
                f1.getRate())).limit(count).collect(Collectors.toList());
    }

    public List<Mpa> getAllMpa() {
        return filmStorage.getAllMpa();
    }

    public Mpa getMpa(int id) {
        return filmStorage.getMpa(id);
    }

    public List<Genre> getAllGenres() {
        return filmStorage.getAllGenres();
    }

    public Genre getGenre(int id) {
        return filmStorage.getGenre(id);
    }

    private Film findId(Integer id, Integer userId) {
        filmValidator.validatorParameter(id, userId);
        Film film = filmStorage.getFilm(id);
        userStorage.findUserById(userId);
        return film;
    }

    private static int compare(int f0, int f1) {
        return f1 - f0; //прямой порядок сортировки
    }
}
