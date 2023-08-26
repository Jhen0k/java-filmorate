package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Data
@Component
public class FilmValidator {
    private int maxDescriptionLength = 200;
    private LocalDate releaseDate = LocalDate.parse("1895-12-28");
    
    public boolean validatorFilm(Film film) {

         if (film.getDescription().length() > maxDescriptionLength) {
             return false;
         }
         if (film.getReleaseDate().isBefore(releaseDate)) {
             return false;
         }
         if (film.getDuration() < 0) {
             return false;
         }
         return true;
    }
}
