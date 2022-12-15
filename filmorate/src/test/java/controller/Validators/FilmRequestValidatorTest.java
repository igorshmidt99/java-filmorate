package controller.Validators;

import module.Components.Film;
import module.Exceptions.Invalid.InvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmRequestValidatorTest {
    static Film film;
    static Validator<Film> validator = new FilmRequestValidator();

    @BeforeEach
    void setUp() {
        film = Film.builder()
                .id(1)
                .name("The Green Mile")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(Duration.ofMinutes(90))
                .description("Jest")
                .build();
    }
    @Test
    void whenFilmNameIsBlankShouldThrowException() {
        film = film.toBuilder().name("").build();
        InvalidException e = assertThrows(InvalidException.class, () -> validator.validate(film));
        assertEquals(e.getMessage(), "Название не может быть пустым.\n");
    }

    @Test
    void whenFilmDescriptionIsMoreThen200CharactersThrowException() {
        film = film.toBuilder().description("Пол Эджкомб — начальник блока смертников в тюрьме «Холодная гора», " +
                "каждый из узников которого однажды проходит «зеленую милю» по пути к месту казни. " +
                "Пол повидал много заключённых и надзирателей за время работы. " +
                "Однако гигант Джон Коффи, обвинённый в страшном преступлении, " +
                "стал одним из самых необычных обитателей блока.").build();
        InvalidException e = assertThrows(InvalidException.class, () -> validator.validate(film));
        assertEquals(e.getMessage(), "Максимальная длина описания — 200 символов.\n");
    }

    @Test
    void whenFilmDateReleaseIsBefore1895ThrowException() {
        film = film.toBuilder().releaseDate(LocalDate.of(1894, 1, 1)).build();
        InvalidException e = assertThrows(InvalidException.class, () -> validator.validate(film));
        assertEquals(e.getMessage(), "Дата релиза — не раньше 28 декабря 1895 года.\n");
    }

    @Test
    void whenFilmDurationIsNegativeThrowException() {
        film = film.toBuilder().duration(Duration.ofMinutes(-90)).build();
        InvalidException e = assertThrows(InvalidException.class, () -> validator.validate(film));
        assertEquals(e.getMessage(), "Продолжительность фильма должна быть положительной.\n");
    }

    @Test
    void whenFilmCollectAllMistakesShouldThrowSpecificMessageWithException() {
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
                .duration(Duration.ofMinutes(-90))
                .build();
        InvalidException e = assertThrows(InvalidException.class, () -> validator.validate(film));
        assertEquals(e.getMessage(), expectedMessage);
    }

    @Test
    void whenIsNoMistakeDoesntThrowException() {
        assertDoesNotThrow(() -> validator.validate(film));
    }
}