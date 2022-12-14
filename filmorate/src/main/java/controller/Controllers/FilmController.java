package controller.Controllers;

import controller.Validators.FilmRequestValidator;
import controller.Validators.Validator;
import lombok.extern.slf4j.Slf4j;
import module.Exceptions.Exist.ExistException;
import module.Exceptions.Invalid.InvalidException;
import org.springframework.web.bind.annotation.*;

import static module.Data.DataStorage.films;
import module.Exceptions.Exist.FilmExistException;
import module.Components.Film;

import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Validator<Film> validator = new FilmRequestValidator();

    @GetMapping
    public Map<Integer, Film> getFilms() {
        log.info("Список фильмов в размере {} передан.", films.size());
        return films;
    }

    @PostMapping
    public void postFilm(@RequestBody Film film) throws FilmExistException {
        try {
            if (films.containsValue(film)) throw new FilmExistException("Этот фильм уже добавлен.");
            validator.validate(film);
            films.put(film.getId(), film);
            log.info("Фильм {} добавлен в хранилище.", film.getName());
        } catch (InvalidException | ExistException e) {
            log.warn(e.getMessage(), e);
        }
    }

    @PutMapping
    public void putFilm(@RequestBody Film film) throws FilmExistException {
        try {
            if (!films.containsKey(film.getId())) throw new FilmExistException("Этого фильма нет в коллекции.");
            validator.validate(film);
            films.put(film.getId(), film);
            log.info("Информация о фильме {} изменена.", film.getName());
        } catch (InvalidException | ExistException e) {
            log.warn(e.getMessage(), e);
        }
    }
}