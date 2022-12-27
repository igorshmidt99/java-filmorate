package ru.yandex.practicum.filmorate.controller.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.module.Components.Film;
import ru.yandex.practicum.filmorate.module.Exceptions.Exist.ExistException;
import ru.yandex.practicum.filmorate.module.Exceptions.Exist.FilmExistException;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

/**
 * Это контроллер, который обрабатывает GET, POST & PUT запросы по пути /films
 * */

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmStorage filmStorage;

    public FilmController(InMemoryFilmStorage inMemoryFilmStorage) {
        filmStorage = inMemoryFilmStorage;
    }

    @GetMapping
    public List<Film> getFilms() {
        List<Film> films = filmStorage.getFilms();
        log.info("Список фильмов в размере {} передан.", films.size());
        return films;
    }

    @PostMapping
    public Film postFilm(@Valid @RequestBody Film film) throws FilmExistException {
        try {
            film = filmStorage.add(film);
            log.info("Фильм {} добавлен в хранилище. ID #{}", film.getName(), film.getId());
            return film;
        } catch (ValidationException | ExistException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) throws FilmExistException {
        try {
            film = filmStorage.update(film);
            log.info("Информация о фильме {} изменена.", film.getName());
            return film;
        } catch (ValidationException | ExistException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}