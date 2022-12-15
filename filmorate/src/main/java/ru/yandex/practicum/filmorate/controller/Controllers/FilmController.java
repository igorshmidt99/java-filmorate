package ru.yandex.practicum.filmorate.controller.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.Validators.FilmRequestValidator;
import ru.yandex.practicum.filmorate.controller.Validators.Validator;
import ru.yandex.practicum.filmorate.module.Components.Film;
import ru.yandex.practicum.filmorate.module.ComponentsManager;
import ru.yandex.practicum.filmorate.module.Exceptions.Exist.ExistException;
import ru.yandex.practicum.filmorate.module.Exceptions.Exist.FilmExistException;
import ru.yandex.practicum.filmorate.module.Exceptions.Invalid.InvalidException;

import java.util.Map;

import static ru.yandex.practicum.filmorate.module.DataStorage.films;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Validator<Film> validator = new FilmRequestValidator();

    @GetMapping
    public Map<Integer, Film> getFilms() {
        log.info("Список фильмов в размере {} передан.", films.size());
        return films;
    }

    @PostMapping
    public Film postFilm(@RequestBody Film film) throws FilmExistException, InvalidException {
        try {
            if (films.containsValue(film)) throw new FilmExistException("Этот фильм уже добавлен.");
            validator.validate(film);
            ComponentsManager.setId(film);
            films.put(film.getId(), film);
            log.info("Фильм {} добавлен в хранилище.", film.getName());
            return film;
        } catch (InvalidException | ExistException e) {
            log.warn(e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping
    public Film putFilm(@RequestBody Film film) throws FilmExistException, InvalidException {
        try {
            if (!films.containsKey(film.getId())) throw new FilmExistException("Этого фильма нет в коллекции.");
            validator.validate(film);
            films.put(film.getId(), film);
            log.info("Информация о фильме {} изменена.", film.getName());
            return film;
        } catch (InvalidException | ExistException e) {
            log.warn(e.getMessage(), e);
            throw e;
        }
    }
}