package ru.yandex.practicum.filmorate.module.Components;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Film {
    private int id;
    @NotEmpty(message = "Название не может быть пустым.")
    @NotNull(message = "Название не может содержать null")
    private final String name;
    @NotNull(message = "Дата релиза не может содержать null")
    private final LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private final int duration;
    @Size(max=200, message = "Максимальная длина описания — 200 символов.")
    @NotNull(message = "Описание не может содержать null")
    private String description;
}