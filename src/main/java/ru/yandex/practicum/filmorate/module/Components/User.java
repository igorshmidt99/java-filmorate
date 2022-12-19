package ru.yandex.practicum.filmorate.module.Components;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private int id;
    @Email(message = "Электронная почта должна соответствовать формату")
    @NotEmpty(message = "Электронная почта не может быть пустой")
    @NotNull(message = "Электронная почта не может содержать null")
    private String email;
    @NotEmpty(message = "Логин не может быть пустым")
    @NotNull(message = "Логин содержит null")
    private final String login;
    private String name;
    @NotNull(message = "Поле с днем рождения содержит null")
    @Past(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;
}