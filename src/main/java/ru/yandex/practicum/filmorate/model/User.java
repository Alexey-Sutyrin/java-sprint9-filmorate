package ru.yandex.practicum.filmorate.model; //Film и User в одном формате написания

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
public class User {
    private int id;
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String name;
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат Email")
    private String email;
    @NotBlank(message = "Логин не может быть пустым")
    private String login;
    @NonNull
    private LocalDate birthday;
}
