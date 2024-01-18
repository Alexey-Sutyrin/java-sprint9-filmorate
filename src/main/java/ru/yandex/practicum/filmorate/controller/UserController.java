package ru.yandex.practicum.filmorate.controller; // Fix 3 - добавляем зависимость в Pom, пишем @Valid

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private int IdUser = 1;
    private final Map<Integer, User> users = new HashMap<>();

    //получение списка пользователей
    @GetMapping
    public Collection<User> getUsers() {

        return users.values();
    }

    //создание пользователя
    @PostMapping
    public User create(@Valid @RequestBody User user) {

        for (User registeredUser : users.values()) {

            if (registeredUser.getEmail().equals(user.getEmail())) {

                log.warn("Пользователь с электронной почтой " + user.getEmail()
                        + " уже зарегистрирован");
                throw new ValidationException();
            }
        }
        validateUser(user);
        user.setId(getIdUser());
        users.put(user.getId(), user);
        log.info("Добавлен новый пользователь");
        return user;
    }

    //обновление пользователя
    @PutMapping
    public User update(@Valid @RequestBody User user) {

        validateUser(user);
        if (!users.containsKey(user.getId())) {

            log.warn("Невозможно обновить пользователя");
            throw new ValidationException();
        }
        users.put(user.getId(), user);
        log.info("Пользователь с id " + user.getId() + " обновлён");
        return user;
    }
    //создание уникального id для пользователя
    private int getIdUser() {

        return IdUser++;
    }

    private void validateUser(User user) {

        validateEmail(user.getEmail());
        validateLogin(user.getLogin());
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {

            user.setName(user.getLogin());
        }
        validateBirthday(user.getBirthday());
    }

    // дополнительные проверки аналогично Film Controller
    private void validateEmail(String email) {

        if (email == null || !email.contains("@") || email.isBlank()) {

            log.warn("Ошибка валидации пользователя. Некорректный адрес электронной почты");
            throw new ValidationException();
        }
    }

    private void validateLogin(String login) {
        if (login == null || login.isBlank() || login.contains(" ")) {

            log.warn("Ошибка валидации пользователя. Некорректный логин");
            throw new ValidationException();
        }
    }

    private void validateBirthday(LocalDate birthday) {

        if (birthday == null || birthday.isAfter(LocalDate.now())) {

            log.warn("Ошибка валидации пользователя. Дата рождения не может быть в будущем");
            throw new ValidationException();
        }
    }
}

