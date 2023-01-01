package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.module.component.Film;
import ru.yandex.practicum.filmorate.module.component.User;
import ru.yandex.practicum.filmorate.module.exception.Exist.FilmExistException;
import ru.yandex.practicum.filmorate.module.exception.Exist.UserExistException;
import ru.yandex.practicum.filmorate.module.exception.LikeException;
import ru.yandex.practicum.filmorate.storage.InMemoryStorages.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryStorages.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmServiceTest {
    private Film film;
    private User user;
    private final FilmStorage filmStorage = new InMemoryFilmStorage();
    private final UserStorage userStorage = new InMemoryUserStorage();
    private final FilmService service = new FilmService(filmStorage, userStorage);

    @BeforeEach
    void setUp() {
        user = new User(0, "igoremail@gmail.com", "igorkiller2010","Igor Shmidt",
                LocalDate.of(1999, 4, 7));
        film = new Film(0, "The Green Mile",
                LocalDate.of(2000, 1, 1), 90, "Jest");
        userStorage.add(user);
        filmStorage.add(film);
    }

    @AfterEach
    void tearDown() {
        userStorage.delete(user);
        filmStorage.delete(film);
    }

    @Test
    public void plusLikeWhenThereAreNoLikesByThisUser() throws UserExistException, FilmExistException, LikeException {
        assertEquals(0, film.getLikesCount());
        service.plusLike(film.getId(), user.getId());
        assertEquals(1, film.getLikesCount());
    }

    @Test
    public void plusLikeWhenFilmKeepLikeByThisUser() throws UserExistException, FilmExistException, LikeException {
        service.plusLike(film.getId(), user.getId());
        assertThrows(LikeException.class, () -> service.plusLike(film.getId(), user.getId()));
    }

    @Test
    public void minusLikeWhenFilmDoesntHasLikesAtAllAndByThisUserInParticular() {
        assertThrows(LikeException.class, () -> service.minusLike(film.getId(), user.getId()));
    }

    @Test
    public void minusLikeWhenFilmKeepOneLike() throws UserExistException, FilmExistException, LikeException {
        service.plusLike(film.getId(), user.getId());
        assertEquals(1, film.getLikesCount());
        service.minusLike(film.getId(), user.getId());
    }

    @Test
    public void getPopularFilms() throws UserExistException, FilmExistException, LikeException {
        List<Film> films = new ArrayList<>();
        int counter = 0;
        while (counter < 10) {
            films.add(film);
            for (int i = 0; i < (10 - counter); i++) {
                service.plusLike(film.getId(), user.getId());
                user = user.toBuilder().id(0).email("igoremail" + i + counter + "@gmail.com").build();
                userStorage.add(user);
            }
            film = film.toBuilder().id(0).name(film.getName() + counter).build();
            filmStorage.add(film);
            counter++;
        }
        films.sort((film1, film2) -> film2.getLikesCount().compareTo(film1.getLikesCount()));
        assertEquals(films, service.getPopularFilms(10));

        int filmId = 2;
        while (filmStorage.getFilms().size() != 1) {
            filmStorage.delete(filmStorage.getById(filmId));
            filmId++;
        }

        int userId = 2;
        while (userStorage.getUsers().size() != 1) {
            userStorage.delete(userStorage.getById(userId));
            userId++;
        }

        user = userStorage.getById(1);
        film = filmStorage.getById(1);
    }
}