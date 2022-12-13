package controller;

import controller.Exceptions.Exist.FilmExistException;
import lombok.extern.slf4j.Slf4j;
import module.Film;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private static final Map<Integer, Film> films = new HashMap();

    @GetMapping
    public Map<Integer, Film> getFilms() {
        log.info("Список фильмов в размере {} передан.", films.size());
        return films;
    }

    @PostMapping
    public Film postFilm(@RequestBody Film film) throws FilmExistException {
        if (films.containsValue(film)) throw new FilmExistException("Этот фильм уже добавлен.");
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film putFilm(@RequestBody Film film) throws FilmExistException {
        if (!films.containsKey(film.getId())) throw new FilmExistException("Этого фильма нет в коллекции.");
        films.put(film.getId(), film);
        return film;
    }
}