package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor
public class FilmRepositoryImpl implements FilmRepository {

    private final Map<Long, Film> filmRepository;
    private final AtomicLong filmCounter;

    @Override
    public Film createFilm(Film film) {
        film.setId(filmCounter.incrementAndGet());
        filmRepository.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getFilm(Long id) {
        Film film = new Film();
        film.setId(filmCounter.incrementAndGet());
        return filmRepository.getOrDefault(id, film);
    }

    @Override
    public Film updateFilm(Film film) {
        return filmRepository.putIfAbsent(film.getId(), film);
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(filmRepository.values());
    }
}
