package ru.yandex.practicum.filmorate.module.Components;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class Film {
    @NonNull
    private int id;
    private final String name;
    private final LocalDate releaseDate;
    private final Duration duration;
    @NonNull
    private String description;
}