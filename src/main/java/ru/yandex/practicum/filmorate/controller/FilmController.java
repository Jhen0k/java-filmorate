package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectValueException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Api(tags = "FilmController", description = "Операции с фильмами")
public class FilmController {


    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @ApiOperation("Добавление фильма")
    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) {
        log.info(film.toString());
        return filmStorage.create(film);
    }

    @ApiOperation("Обновление фильма")
    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        return filmStorage.update(film);
    }

    @ApiOperation("Получение всех фильмов")
    @GetMapping("/films")
    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    @ApiOperation("Получение фильма по ID")
    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable Integer id) {
        return filmStorage.getFilm(id);
    }

    @ApiOperation("Добавление лайка к фильму.")
    @PutMapping("/films/{id}/like/{userId}")
    public String addLikeToFilm(
            @PathVariable Integer id,
            @PathVariable Integer userId
    ) {
        return filmService.addLike(id, userId);
    }

    @ApiOperation("Удаление лайка к фильму.")
    @DeleteMapping("/films/{id}/like/{userId}")
    public String removeLikeToFilm(
            @PathVariable Integer id,
            @PathVariable Integer userId
    ) {
        return filmService.removeLike(id, userId);
    }

    @ApiOperation("Получение 10 популярных фильма.")
    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10", required = false) Integer count) {
        if (count < 0) {
            throw new IncorrectValueException(count);
        }
        log.info(String.format("Запрос на %s лучших фильмов.", count));
        log.info(String.format("Вернулось %s фильмов", filmService.getTenPopularFilms(count).size()));
        return filmService.getTenPopularFilms(count);
    }

    @ApiOperation("Получение всех рейтингов.")
    @GetMapping("/mpa")
    public List<Mpa> getAllMpa() {
        return filmService.getAllMpa();
    }

    @ApiOperation("Получение рейтинга по id.")
    @GetMapping("/mpa/{id}")
    public Mpa getMpa(@PathVariable Integer id) {
        return filmService.getMpa(id);
    }

    @ApiOperation("Получение всех жанров.")
    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        return filmService.getAllGenres();
    }

    @ApiOperation("Получение жанра по id.")
    @GetMapping("/genres/{id}")
    public Genre getGenre(@PathVariable Integer id) {
        return filmService.getGenre(id);
    }
}
