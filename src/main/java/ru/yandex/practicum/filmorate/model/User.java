package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {

    private final Set<Long> friends = new HashSet<>();
    private Long id;
    @NotBlank(message = "Адрес e-mail не может быть пустым")
    @Email(message = "Адрес электронной почты введен некорректно")
    private final String email;
    @NotBlank(message = "Логин не может быть пустым")
    private final String login;
    @NotNull
    @PastOrPresent(message = "День рождения не может быть в будущем")
    private final LocalDate birthday;
    private String name;
}
