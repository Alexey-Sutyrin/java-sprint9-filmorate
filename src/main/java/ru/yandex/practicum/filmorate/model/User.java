package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
public class User {

    private Integer id;
    // Fix 2 -Нужно единообразие в стиле, лучше аннотации разместить как в User сделано
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String name;
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат Email")
    private final String email;
    @NotBlank(message = "Логин не может быть пустым")
    private final String login;
    private final LocalDate birthday;

}
