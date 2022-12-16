package ru.yandex.practicum.filmorate.controller.Validators;

import ru.yandex.practicum.filmorate.module.Exceptions.Invalid.FilmInvalidException;
import ru.yandex.practicum.filmorate.module.Components.Film;

import java.time.LocalDate;

public class FilmRequestValidator implements Validator<Film> {
    @Override
    public void validate(Film film) throws FilmInvalidException {
        StringBuilder errorText = new StringBuilder();
        if (film.getName().isBlank())
            errorText.append("Название не может быть пустым.\n");
        if (film.getDescription().length() > 200)
            errorText.append("Максимальная длина описания — 200 символов.\n");
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            errorText.append("Дата релиза — не раньше 28 декабря 1895 года.\n");
        if (film.getDuration() < 0)
            errorText.append("Продолжительность фильма должна быть положительной.\n");
        if (!errorText.toString().isEmpty()) throw new FilmInvalidException(errorText.toString());
    }
}