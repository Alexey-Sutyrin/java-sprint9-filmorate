package ru.yandex.practicum.filmorate.model;//Fix - NotNull для строк исправил на NotBlank

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Film {

    private final Set<Long> likes = new HashSet<>();
    private final Set<Genre> genres = new HashSet<>();
    private Long id;
    @NotBlank(message = "Название фильма не может быть пустым")
    private final String name;
    @Length(max = 200, message = "Максимальная длина описания — 200 символов")
    @NotBlank(message = "Описание не может быть пустым")
    private final String description;
    @Past(message = "Дата релиза должна быть в прошлом")
    @NotNull
    private final LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма не может быть отрицательной")
    @NotNull
    private final Integer duration;
    @NotNull
    private final Mpa mpa;
}
