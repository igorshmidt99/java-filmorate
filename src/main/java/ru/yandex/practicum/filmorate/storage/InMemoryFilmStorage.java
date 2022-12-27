package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.module.Components.Film;
import ru.yandex.practicum.filmorate.module.Exceptions.Exist.FilmExistException;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class InMemoryFilmStorage implements FilmStorage {

    @Getter
    private static int filmIdCounter;

    private static final Map<Integer, Film> films = new HashMap<>();
    @Override
    public Film add(Film film) throws FilmExistException {
        if (films.containsKey(film.getId()))
            throw new FilmExistException("Этот фильм уже добавлен.");
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года.");
        createId(film);
        return film;
    }

    @Override
    public Film update(Film film) throws FilmExistException {
        if (!films.containsKey(film.getId())) throw new FilmExistException("Этого фильма нет в коллекции.");
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film delete(Film film) {
        return null;
    }

    @Override
    public List<Film> getFilms() {
        return List.copyOf(films.values());
    }

    private void createId(Film film) {
        filmIdCounter++;
        film.setId(filmIdCounter);
    }
}