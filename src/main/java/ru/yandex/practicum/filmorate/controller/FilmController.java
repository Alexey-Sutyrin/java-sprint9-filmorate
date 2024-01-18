package ru.yandex.practicum.filmorate.controller; // Fix 1 - Логирование лучше в начало метода поместить

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
@Validated
public class FilmController {

    public static final int MAX_CHARS_AMOUNT = 200; // максимальное количество букв в описании ячейке
    private final Map<Integer, Film> films = new HashMap<>();
    private Integer idFilm = 1;

    @GetMapping
    public Collection<Film> getFilms() {

        return films.values();
    }

    // добавление фильма в список
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {

        log.info("Добавлен новый фильм {}", film);
        validateFilm(film);
        film.setId(getIdFilm());
        films.put(film.getId(), film);
        return film;
    }

    //обновление фильма в списке
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {

        validateFilm(film);
        if (!films.containsKey(film.getId())) {

            log.warn("Невозможно обновить фильм");
            throw new ValidationException();
        }
        films.put(film.getId(), film);
        log.info("Фильм c id" + film.getId() + " был обновлён");
        return film;
    }

    //создание уникального id для фильма
    private int getIdFilm() {

        return idFilm++;
    }

    private void validateFilm(Film film) {

        validateName(film.getName());
        validateDescriptionLength(film.getDescription());
        validateDate(film.getReleaseDate());
        validateDuration(film.getDuration());
    }

    // проверка названия фильма
    private void validateName(String name) {

        if (name == null || name.isBlank() || name.isEmpty()) {

            log.warn("Ошибка валидации фильма. Название не может быть пустым");
            throw new ValidationException();
        }
    }

    // проверка описания фильма
    private void validateDescriptionLength(String description) {

        if (description == null || description.length() > MAX_CHARS_AMOUNT) {

            log.warn("Ошибка валидации фильма. Некорректное описание фильма");
            throw new ValidationException();
        }
    }

    // проверка даты релиза фильма
    private void validateDate(LocalDate releaseDate) {

        if (releaseDate == null || releaseDate.isBefore(LocalDate.of(1895, 12, 28))
                || releaseDate.isAfter(LocalDate.now())) {
            log.warn("Ошибка валидации фильма. Некорректная дата релиза фильма");
            throw new ValidationException();
        }
    }

    // проверка продолжительности фильма
    private void validateDuration(Integer duration) {

        if (duration == null || duration < 0) {
            log.warn("Ошибка валидации фильма. Продолжительность фильма должна быть положительной");
            throw new ValidationException();
        }
    }
}
