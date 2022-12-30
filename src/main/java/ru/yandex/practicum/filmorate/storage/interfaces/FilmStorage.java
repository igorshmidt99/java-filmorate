package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.module.component.Film;
import ru.yandex.practicum.filmorate.module.exception.Exist.FilmExistException;

import java.util.List;

public interface FilmStorage {
    Film add(Film film) throws FilmExistException;
    Film update(Film film) throws FilmExistException;
    Film delete(Film film) throws FilmExistException;
    List<Film> getFilms();
    Film getById(long id) throws FilmExistException;
}