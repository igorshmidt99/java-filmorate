package ru.yandex.practicum.filmorate.module.validator;

import ru.yandex.practicum.filmorate.module.exception.Invalid.FilmInvalidException;
import ru.yandex.practicum.filmorate.module.component.Film;

import java.time.LocalDate;
/**
 * Реализует интерфейс Validator.
 * <p>
 *     Метод validate() проверяет поля объекта согласно требованиям:
 *     <p>
 *         name не может быть пустым.
 *         <p>
 *         description не может иметь длину более 200 символов.
 *         <p>
 *         releaseDate не может быть раньше 28 декабря 1895 года.
 *         <p>
 *         duration должен быть положительным.
 *         <p>
 *         Если состояние объекта не удовлетворяет требованиям, то в сообщение об ошибке вносится соответствующий текст, который передается в конструктор исключения.
 * */
public class FilmRequestValidator implements Validator<Film> {
    /**
     * @param film передается c запросом.
     * @throws FilmInvalidException в случае неудовлетворения требованиям.
     * */
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