package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.IncorrectValueException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.FilmValidator;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@RestController
@RequestMapping("/films")
@AllArgsConstructor
@Slf4j
@Api(tags = "FilmController", description = "Операции с фильмами")
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmService filmService;
    private final FilmValidator filmValidator;

    @ApiOperation("Добавление фильма")
    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        return filmStorage.create(film);
    }

    @ApiOperation("Обновление фильма")
    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        return filmStorage.update(film);
    }

    @ApiOperation("Получение всех фильмов")
    @GetMapping()
    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    @ApiOperation("Получение фильма по ID")
    @GetMapping("/{id}")
    public Film getFilm( @PathVariable Integer id) {
        return filmStorage.getFilm(id);
    }

    @ApiOperation("Добавление лайка к фильму.")
    @PutMapping("{id}/like/{userId}")
    public String addLikeToFilm(
            @PathVariable Integer id,
            @PathVariable Integer userId
    ) {
        filmValidator.validatorParameter(id, userId);
        return filmService.addLike(id, userId);
    }

    @ApiOperation("Удаление лайка к фильму.")
    @DeleteMapping("/{id}/like/{userId}")
    public String removeLikeToFilm(
            @PathVariable Integer id,
            @PathVariable Integer userId
    ) {
        filmValidator.validatorParameter(id, userId);
        return filmService.removeLike(id, userId);
    }

    @ApiOperation("Получение 10 популярных фильма.")
    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10", required = false) Integer count) {
        if (count < 0) {
            throw new IncorrectValueException(count);
        }
        log.info(String.format("Запрос на %s лучших фильмов.", count));
        log.info(String.format("Вернулось %s фильмов", filmService.getTenPopularFilms(count).size()));
        return filmService.getTenPopularFilms(count);
    }
}
