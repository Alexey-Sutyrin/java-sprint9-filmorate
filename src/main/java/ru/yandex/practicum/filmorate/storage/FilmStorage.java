package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    List<Film> getFilms();

    Film create(Film film);

    Film update(Film film);

    Film findFilmById(Long id);

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);
}
