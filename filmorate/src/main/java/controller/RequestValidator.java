package controller;

import controller.Exceptions.Exist.FilmExistException;
import module.Film;

import java.time.LocalDate;

public class RequestValidator {
    public static void filmValidator(Film film) throws FilmExistException {
        StringBuilder errorText = new StringBuilder();
        if (film.getName().isBlank())
            errorText.append("Название не может быть пустым.\n");
        if (film.getDescription().length() > 200)
            errorText.append("Максимальная длина описания — 200 символов.\n");
        if (film.getReleaseDate().isBefore(LocalDate.of(1985, 12, 28)))
            errorText.append("Дата релиза — не раньше 28 декабря 1895 года.\n");
        if (film.getDuration().isNegative())
            errorText.append("Продолжительность фильма должна быть положительной.\n");
        if (!errorText.toString().isEmpty()) throw new FilmExistException(errorText.toString());
    }
}