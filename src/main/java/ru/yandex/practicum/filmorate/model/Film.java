package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Data
@Builder
public class Film {

    private Integer id;
    @NotBlank(message = "Название фильма не может быть пустым")
    private final String name;
    @NotBlank(message = "Описание фильма не может быть пустым")
    private final String description;
    @PositiveOrZero(message = "Продолжительность фильма не может быть отрицательной")
    private final Integer duration;
    private final LocalDate releaseDate;
}
