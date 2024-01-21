package ru.yandex.practicum.filmorate.model; //Fix - NotNull для строк исправил на NotBlank

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
    @Email(message = "Адрес e-mail введен некорректно")
    private final String email;
    @NotBlank(message = "Логин не может быть пустым")
    private final String login;
    @NotNull
    @PastOrPresent(message = "День рождения не может быть в будущем")
    private final LocalDate birthday;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
}
