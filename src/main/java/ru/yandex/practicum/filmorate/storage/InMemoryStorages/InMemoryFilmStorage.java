package ru.yandex.practicum.filmorate.storage.InMemoryStorages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.module.component.Film;
import ru.yandex.practicum.filmorate.module.exception.Exist.FilmExistException;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final static LocalDate FIRST_MOVIE_DATE = LocalDate.of(1895, 12, 28);
    private static long filmsIdCounter;
    private static final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film add(Film film) throws FilmExistException {
        if (films.containsKey(film.getId()))
            throw new FilmExistException("Этот фильм уже добавлен.");
        if (film.getReleaseDate().isBefore(FIRST_MOVIE_DATE))
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года.");
        createId(film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) throws FilmExistException {
        if (!films.containsKey(film.getId())) throw new FilmExistException("Этого фильма нет в коллекции.");
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film delete(Film film) throws FilmExistException {
        if (!films.containsKey(film.getId())) throw new FilmExistException("Этого фильма нет в коллекции.");
        films.remove(film.getId());
        return film;
    }

    @Override
    public List<Film> getFilms() {
        return List.copyOf(films.values());
    }

    @Override
    public Film getById(long id) throws FilmExistException {
        if (!films.containsKey(id)) throw new FilmExistException("Этого фильма нет в коллекции.");
        return films.get(id);
    }

    private void createId(Film film) {
        film.setId(++filmsIdCounter);
    }
}