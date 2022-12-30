package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.module.component.Film;
import ru.yandex.practicum.filmorate.module.exception.Exist.ExistException;
import ru.yandex.practicum.filmorate.module.exception.Exist.FilmExistException;
import ru.yandex.practicum.filmorate.module.exception.Exist.UserExistException;
import ru.yandex.practicum.filmorate.module.exception.LikeException;
import ru.yandex.practicum.filmorate.service.FilmService;
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
    private final FilmService filmService;
    private final FilmStorage filmStorage;

    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
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
            if (film == null) throw new FilmExistException("Этот фильм уже добавлен.");
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
            if (film == null) throw new FilmExistException("Этого фильма нет в коллекции.");
            log.info("Информация о фильме {} изменена.", film.getName());
            return film;
        } catch (ValidationException | ExistException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(
            @PathVariable long id,
            @PathVariable long userId
    ) throws UserExistException, FilmExistException, LikeException {
        return filmService.plusLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(
            @PathVariable long id,
            @PathVariable long userId
    ) throws UserExistException, FilmExistException, LikeException {
        return filmService.minusLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") long count) {
        List<Film> f = filmService.getPopularFilms(count);
        log.info("Список популярных фильмов в размере {} передан.", f.size());
        return f;
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable long id) throws FilmExistException {
        try {
            Film film = filmStorage.getById(id);
            if (film == null) throw new FilmExistException("Этого фильма нет в коллекции.");
            log.info("Фильм {} передан.", film.getName());
            return film;
        } catch (FilmExistException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}