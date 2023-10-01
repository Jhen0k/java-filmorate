package ru.yandex.practicum.filmorate.storage;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmStorageTest {

    public final FilmStorage filmStorage;

    @Test
    public void findFilmById() {
        Film newFilm = Film.builder().name("New film").releaseDate(LocalDate.parse("1999-04-30"))
                .description("New film about friends").duration(120).rate(4).mpa(new Mpa(3, null)).build();

        filmStorage.create(newFilm);
        Optional<Film> filmOptional = Optional.of(filmStorage.getFilm(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void getAllFilms() {
        Film newFilm1 = Film.builder().name("New film").releaseDate(LocalDate.parse("1999-04-30"))
                .description("New film about friends").duration(120).rate(4).mpa(new Mpa(3, null)).build();

        filmStorage.create(newFilm1);

        List<Film> filmList = filmStorage.getFilms();

        assertNotNull(filmList, "Список пуст.");
        assertEquals(2, filmList.size(), "Длина списка фильмов не совпадает с ожидаемым: 2");
    }

    @Test
    public void updateFilm() {
        Film newFilm = Film.builder().id(1).name("Film Updated").releaseDate(LocalDate.parse("1989-04-17"))
                .description("New film update decription").duration(190).rate(4).mpa(new Mpa(2, null)).build();

        filmStorage.update(newFilm);

        Optional<Film> filmOptional = Optional.of(filmStorage.getFilm(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );

        assertEquals(newFilm.getName(), filmOptional.get().getName(), "Имена не совпадают.");
        assertEquals(newFilm.getDescription(), filmOptional.get().getDescription(), "Описание не совпадает.");
        assertEquals(newFilm.getDuration(), filmOptional.get().getDuration(), "длительность не совпадает.");
        assertEquals(newFilm.getRate(), filmOptional.get().getRate(), "Рейтинг не совпадает.");
    }

    @Test
    public void createFilm() {
        Film newFilm = Film.builder().name("New film").releaseDate(LocalDate.parse("1999-04-30"))
                .description("New film about friends").duration(120).rate(4).mpa(new Mpa(3, null)).build();

        filmStorage.create(newFilm);

        Optional<Film> filmOptional = Optional.of(filmStorage.getFilm(2));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 2)
                );

        assertEquals(newFilm.getName(), filmOptional.get().getName(), "Имена не совпадают.");
        assertEquals(newFilm.getDescription(), filmOptional.get().getDescription(), "Описание не совпадает.");
        assertEquals(newFilm.getDuration(), filmOptional.get().getDuration(), "длительность не совпадает.");
        assertEquals(newFilm.getRate(), filmOptional.get().getRate(), "Рейтинг не совпадает.");
    }

    @Test
    public void findMpaById() {
        Optional<Mpa> mpaOptional = Optional.of(filmStorage.getMpa(1));

        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa ->
                        assertThat(mpa).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void getAllMpa() {
        List<Mpa> mpaList = filmStorage.getAllMpa();

        assertNotNull(mpaList, "Список пуст.");
        assertEquals(5, mpaList.size(), "Длина списка не совпадает с ожидаемым: 5.");
    }

    @Test
    public void findGenreById() {
        Optional<Genre> genreOptional = Optional.of(filmStorage.getGenre(1));

        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genre ->
                        assertThat(genre).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void getAllGenres() {
        List<Genre> genreList = filmStorage.getAllGenres();

        assertNotNull(genreList, "Список пуст");
        assertEquals(6, genreList.size(), "Длина списка не совпадает с ожидаемым: 6.");
    }
}
