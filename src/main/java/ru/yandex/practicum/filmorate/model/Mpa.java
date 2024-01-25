package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class Mpa {

    private final int id;
    @NotBlank(message = "Название рейтинга не может быть пустым")
    private final String name;
}
