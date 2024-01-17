package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Getter
@Setter
@Data
@Builder
public class Film {
    private int id;
    @NonNull private String name;
    @NonNull private String description;
    @NonNull private LocalDate releaseDate;
    @NonNull private int duration;
}