package ru.yandex.practicum.filmorate.controller; // Update 1 for review 1 - restore

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    //Хранение списка добавленных фильмов

    private final Map<Integer, Film> films = new HashMap<>();
    private int idFilm = 0;
    //получение списка всех фильмов

    @GetMapping
    public List<Film> getFilms() {

        return new ArrayList<>(films.values());
    }
    //добавление фильма в список

    @PostMapping()
    public Film addFilm(@RequestBody Film film) throws ValidationException {

        FilmValidator.isValidFilms(film);
        int id = generateIdFilms();
        film.setId(id);
        log.debug("Сохранение: {}", film);
        films.put(film.getId(), film);
        return film;
    }
    //обновление фильма в списке

    @PutMapping()
    public Film updateFilm(@RequestBody @NotNull Film film) throws ValidationException {

        if (films.containsKey(film.getId())) {

            FilmValidator.isValidFilms(film);
            log.debug("Обновление: {}", film);
            films.put(film.getId(), film);

        } else {

            throw new ValidationException("Такого фильма нет в базе Filmorate.");

        }
        return film;
    }
    //создание уникального id для фильма

    private int generateIdFilms() {

        return ++idFilm;
    }
}