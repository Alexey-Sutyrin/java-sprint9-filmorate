package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
@Validated
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int idUser = 1;
    //получение списка пользователей

    @GetMapping
    public List<User> getUsers() {

        return new ArrayList<>(users.values());
    }
    //создание пользователя

    @PostMapping()
    public User addUser(@Valid @RequestBody User user) throws ValidationException {
        log.debug("Сохранено: {}", user);
        UserValidator.isValidUsers(user);
        int i = generateIdUsers();
        user.setId(i);
        users.put(user.getId(), user);
        return user;
    }
    //обновление пользователя

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {

        if (users.containsKey(user.getId())) {

            log.debug("Обновлено: {}", user);
            UserValidator.isValidUsers(user);
            users.put(user.getId(), user);
        } else {

            throw new ValidationException("Такого пользователя нет в базе.");
        }
        return user;
    }
    //создание уникального id для пользователя

    private int generateIdUsers() {

        return idUser++;
    }

    private void validateUser(User user) {
        validateEmail(user.getEmail());
        validateLogin(user.getLogin());
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        validateBirthday(user.getBirthday());
    }

    private void validateEmail(String email) {
        if (email == null || !email.contains("@") || email.isBlank()) {
            log.warn("Ошибка валидации пользователя. Некорректный адрес электронной почты");
            throw new ValidationException("Некорректный адрес электронной почты");
        }
    }

    private void validateLogin(String login) {
        if (login == null || login.isBlank() || login.contains(" ")) {
            log.warn("Ошибка валидации пользователя. Некорректный логин");
            throw new ValidationException("Некорректный логин");
        }
    }

    private void validateBirthday(LocalDate birthday) {
        if (birthday == null || birthday.isAfter(LocalDate.now())) {
            log.warn("Ошибка валидации пользователя. Дата рождения не может быть в будущем");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }
}
