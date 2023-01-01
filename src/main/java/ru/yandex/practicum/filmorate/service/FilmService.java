package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.module.component.Film;
import ru.yandex.practicum.filmorate.module.component.User;
import ru.yandex.practicum.filmorate.module.exception.Exist.FilmExistException;
import ru.yandex.practicum.filmorate.module.exception.Exist.UserExistException;
import ru.yandex.practicum.filmorate.module.exception.LikeException;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film plusLike(long filmId, long userId) throws UserExistException, FilmExistException, LikeException {
        User user = userStorage.getById(userId);
        if (user == null)
            throw new UserExistException("Этого пользователя не существует.");
        Film film = filmStorage.getById(filmId);
        if (film == null)
            throw new FilmExistException("Этого фильма нет в коллекции.");
        if (film.getLikes().contains(user.getId()))
            throw new LikeException("Этот пользователь уже поставил лайк.");
        film.getLikes().add(user.getId());
        return film;
    }

    public Film minusLike(long filmId, long userId) throws LikeException, UserExistException, FilmExistException {
        User user = userStorage.getById(userId);
        if (user == null)
            throw new UserExistException("Этого пользователя не существует.");
        Film film = filmStorage.getById(filmId);
        if (film == null)
            throw new FilmExistException("Этого фильма нет в коллекции.");
        if (!film.getLikes().contains(user.getId()))
            throw new LikeException("Этот пользователь не ставил лайк.");
        film.getLikes().remove(user.getId());
        return film;
    }

    public List<Film> getPopularFilms(long count) {
        return filmStorage.getFilms().stream()
                .sorted((film1, film2) -> film2.getLikesCount().compareTo(film1.getLikesCount()))
                .limit(count).collect(Collectors.toList());
    }
}