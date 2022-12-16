package ru.yandex.practicum.filmorate.module.Components;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class User {
    @NotNull
    @NonNull
    private int id;
    @Email
    @NotEmpty
    @NotNull
    @NonNull
    private String email;
    @NotEmpty
    @NotNull
    @NonNull
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
}