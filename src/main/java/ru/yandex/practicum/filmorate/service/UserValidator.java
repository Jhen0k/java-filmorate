package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator {

    public boolean validateUser(User user) {

        if (!Objects.isNull(user.getLogin())) {
            Pattern pattern = Pattern.compile("\\s");
            Matcher matcher = pattern.matcher(user.getLogin());
            boolean found = matcher.find();
            if (found) {
                return false;
            }
        } else {
            return false;
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            return false;
        }
        return true;
    }
}
