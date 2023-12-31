package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator {
    private static final Pattern myRegex = Pattern.compile("\\s");

    public boolean validateUser(User user) {

        if (!Objects.isNull(user.getLogin())) {
            Matcher matcher = myRegex.matcher(user.getLogin());
            boolean found = matcher.find();
            if (found) {
                return false;
            }
        } else {
            return false;
        }
        return !user.getBirthday().isAfter(LocalDate.now());
    }

    public void validateParameter(Integer userId, Integer friendId) {
        if (userId < 0) {
            throw new IncorrectParameterException("userId");
        }
        if (friendId < 0) {
            throw new IncorrectParameterException("friendId");
        }
    }


}
