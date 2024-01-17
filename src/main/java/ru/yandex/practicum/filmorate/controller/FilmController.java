package ru.yandex.practicum.filmorate.controller; // Fix - Log Debug перенесен в начало

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    //Хранение списка добавленных фильмов
    public static final int MAX_CHARS_AMOUNT = 200; //максимальная длина названия
    private final Map<Integer, Film> films = new HashMap<>();
    private int idFilm = 1;
    //получение списка всех фильмов

    @GetMapping
    public List<Film> getFilms() {

        return new ArrayList<>(films.values());
    }
    //добавление фильма в список

    @PostMapping()
    public Film addFilm(@RequestBody Film film) throws ValidationException {

        log.debug("Добавление фильма: {}", film);
        FilmValidator.isValidFilms(film);
        int id = generateIdFilms();
        film.setId(id);
        films.put(film.getId(), film);
        log.debug("Добавление успешно!");
        return film;
    }
    //обновление фильма в списке

    @PutMapping()
    public Film updateFilm(@RequestBody @NotNull Film film) throws ValidationException {

        if (films.containsKey(film.getId())) {
            log.debug("Обновление фильма: {}", film);
            FilmValidator.isValidFilms(film);
            films.put(film.getId(), film);
            log.debug("Обновление успешно!");
        } else {

            throw new ValidationException("Такого фильма нет в базе Filmorate.");

        }
        return film;
    }
    //создание уникального id для фильма

    private int generateIdFilms() {

        return idFilm++;
    }

    private void validateFilm(Film film) {
        validateName(film.getName());
        validateDescriptionLength(film.getDescription());
        validateDate(film.getReleaseDate());
        validateDuration(film.getDuration());
    }

    private void validateName(String name) {
        if (name == null || name.isBlank() || name.isEmpty()) {
            log.warn("Ошибка валидации фильма. Название не может быть пустым");
            throw new ValidationException("Название фильма не может быть пустым");
        }
    }

    private void validateDescriptionLength(String description) {
        if (description != null && description.length() > MAX_CHARS_AMOUNT) {
            log.warn("Ошибка валидации фильма.Превышена максимальная длина описания фильма");
            throw new ValidationException("Превышена максимальная длина описания фильма");
        }
    }

    private void validateDate(LocalDate releaseDate) {
        if (releaseDate != null && releaseDate.isBefore(LocalDate.of(1895, 12, 28))
                || (releaseDate != null && releaseDate.isAfter(LocalDate.now()))) {
            log.warn("Ошибка валидации фильма. Некорректная дата релиза фильма");
            throw new ValidationException("Некорректная дата релиза фильма");
        }
    }

    private void validateDuration(Integer duration) {
        if (duration != null && duration < 0) {
            log.warn("Ошибка валидации фильма. Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
