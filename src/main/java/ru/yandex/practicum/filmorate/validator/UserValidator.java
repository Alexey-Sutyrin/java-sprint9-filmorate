package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class UserValidator {

     // Процессы проверки-валидации пользователей в базе

    public static void isValidUsers(@RequestBody User user) throws ValidationException {
        if (user.getEmail().isBlank()) {
            log.warn("Ошибка в e-mail: {}", user);
            throw new ValidationException("Пользователь не соответствует условиям: " +
                    "e-mail не должен быть пустым");
        }
        if (!user.getEmail().contains("@")) {
            log.warn("Ошибка в e-mail: {}", user);
            throw new ValidationException("Пользователь не соответствует условиям: " +
                    "e-mail должен содержать - \"@\"");
        }
        if (user.getLogin().isBlank()) {
            log.warn("Ошибка в логине: {}", user);
            throw new ValidationException("Пользователь не соответствует условиям: " +
                    "логин не должен быть пустым");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            log.debug("Имя пусто - заменено логином: {}", user);
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Ошибка в дате рождения: {}", user);
            throw new ValidationException("Пользователь не соответствует условиям: " +
                    "дата рождения не может быть в будущем времени");
        }
    }
}

