package ru.yandex.practicum.filmorate.module;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
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