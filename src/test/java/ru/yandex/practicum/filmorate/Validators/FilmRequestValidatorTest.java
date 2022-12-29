package ru.yandex.practicum.filmorate.Validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.Exceptions.Invalid.InvalidException;
import ru.yandex.practicum.filmorate.controller.Validators.FilmRequestValidator;
import ru.yandex.practicum.filmorate.controller.Validators.Validator;
import ru.yandex.practicum.filmorate.module.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmRequestValidatorTest {
    private static Film film;
    private static final Validator<Film> validator = new FilmRequestValidator();

    @BeforeEach
    public void setUp() {
        film = new Film(1, "The Green Mile",
                LocalDate.of(2000, 1, 1), 90, "Jest");
    }
    @Test
    public void whenFilmNameIsBlankShouldThrowException() {
        film = film.toBuilder().name("").build();
        InvalidException e = assertThrows(InvalidException.class, () -> validator.validate(film));
        assertEquals(e.getMessage(), "Название не может быть пустым.\n");
    }

    @Test
    public void whenFilmDescriptionIsMoreThen200CharactersThrowException() {
        film = film.toBuilder().description("Пол Эджкомб — начальник блока смертников в тюрьме «Холодная гора», " +
                "каждый из узников которого однажды проходит «зеленую милю» по пути к месту казни. " +
                "Пол повидал много заключённых и надзирателей за время работы. " +
                "Однако гигант Джон Коффи, обвинённый в страшном преступлении, " +
                "стал одним из самых необычных обитателей блока.").build();
        InvalidException e = assertThrows(InvalidException.class, () -> validator.validate(film));
        assertEquals(e.getMessage(), "Максимальная длина описания — 200 символов.\n");
    }

    @Test
    public void whenFilmDateReleaseIsBefore1895ThrowException() {
        film = film.toBuilder().releaseDate(LocalDate.of(1894, 1, 1)).build();
        InvalidException e = assertThrows(InvalidException.class, () -> validator.validate(film));
        assertEquals(e.getMessage(), "Дата релиза — не раньше 28 декабря 1895 года.\n");
    }

    @Test
    public void whenFilmDurationIsNegativeThrowException() {
        film = film.toBuilder().duration(-90).build();
        InvalidException e = assertThrows(InvalidException.class, () -> validator.validate(film));
        assertEquals(e.getMessage(), "Продолжительность фильма должна быть положительной.\n");
    }

    @Test
    public void whenFilmCollectAllMistakesShouldThrowSpecificMessageWithException() {
        String expectedMessage = "Название не может быть пустым.\n" +
                "Максимальная длина описания — 200 символов.\n" +
                "Дата релиза — не раньше 28 декабря 1895 года.\n" +
                "Продолжительность фильма должна быть положительной.\n";
        film = film.toBuilder()
                .name("")
                .description("Пол Эджкомб — начальник блока смертников в тюрьме «Холодная гора», " +
                "каждый из узников которого однажды проходит «зеленую милю» по пути к месту казни. " +
                "Пол повидал много заключённых и надзирателей за время работы. " +
                "Однако гигант Джон Коффи, обвинённый в страшном преступлении, " +
                "стал одним из самых необычных обитателей блока.")
                .releaseDate(LocalDate.of(1894, 1, 1))
                .duration(-90)
                .build();
        InvalidException e = assertThrows(InvalidException.class, () -> validator.validate(film));
        assertEquals(e.getMessage(), expectedMessage);
    }

    @Test
    public void whenIsNoMistakeDoesntThrowException() {
        assertDoesNotThrow(() -> validator.validate(film));
    }
}