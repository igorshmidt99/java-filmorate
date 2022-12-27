package ru.yandex.practicum.filmorate.controller.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.module.Components.Film;
import ru.yandex.practicum.filmorate.module.ComponentsManagers.ComponentsManager;
import ru.yandex.practicum.filmorate.module.Exceptions.Exist.ExistException;
import ru.yandex.practicum.filmorate.module.Exceptions.Exist.FilmExistException;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;

import static ru.yandex.practicum.filmorate.module.ComponentsManagers.ComponentsManager.getFilmsList;
import static ru.yandex.practicum.filmorate.module.ComponentsManagers.ComponentsStorage.films;

/**
 * Это контроллер, который обрабатывает GET, POST & PUT запросы по пути /films
 * */

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    @GetMapping
    public List<Film> getFilms() {
        log.info("Список фильмов в размере {} передан.", films.size());
        return getFilmsList();
    }

    @PostMapping
    public Film postFilm(@Valid @RequestBody Film film) throws FilmExistException {
        try {
            if (films.containsValue(film))
                throw new FilmExistException("Этот фильм уже добавлен.");
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
                throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года.");
            ComponentsManager.createId(film);
            films.put(film.getId(), film);
            log.info("Фильм {} добавлен в хранилище.", film.getName());
            return film;
        } catch (ValidationException | ExistException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) throws FilmExistException {
        try {
            if (!films.containsKey(film.getId())) throw new FilmExistException("Этого фильма нет в коллекции.");
            films.put(film.getId(), film);
            log.info("Информация о фильме {} изменена.", film.getName());
            return film;
        } catch (ValidationException | ExistException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}