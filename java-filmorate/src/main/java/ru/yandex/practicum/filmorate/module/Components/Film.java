package ru.yandex.practicum.filmorate.module.Components;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class Film {
    @NonNull
    private int id;
    @NotEmpty
    @NotNull
    private final String name;
    private final LocalDate releaseDate;
    @Positive
    private final int duration;
    @Size(max=200)
    @NonNull
    private String description;
}