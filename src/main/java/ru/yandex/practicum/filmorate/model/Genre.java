package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class Genre {

    private final int id;
    @NotBlank(message = "Название жанра не может быть пустым")
    private final String name;
}

