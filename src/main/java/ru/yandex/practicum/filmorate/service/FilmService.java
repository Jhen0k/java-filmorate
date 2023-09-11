package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectValueException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public String addLike(Integer id, Integer userId) {
        findId(id, userId);
        filmStorage.getMapFilms().get(id).getLikes().add(userId);
        return String.format("Пользователь с id: %s поставил лайк фильму с id: %s ", userId, id);
    }

    public String removeLike(Integer id, Integer userId) {
        findId(id, userId);
        filmStorage.getMapFilms().get(id).getLikes().remove(userId);
        return String.format("Пользователь с id: %s удалил лайк фильма с id: %s", userId, id);
    }

    public List<Film> getTenPopularFilms(Integer count) {

        return filmStorage.getFilms().stream().sorted((f0, f1) -> compare(f0.getLikes().size()
                , f1.getLikes().size())).limit(count).collect(Collectors.toList());
    }

    private void findId(Integer id, Integer userId) {
        if (!filmStorage.getMapFilms().containsKey(id)) {
            throw new IncorrectValueException(id);
        }
        if (!userStorage.getMapUsers().containsKey(userId)) {
            throw new IncorrectValueException(id);
        }
    }

    private int compare(int f0, int f1) {
        return f1 - f0; //прямой порядок сортировки
    }
}
