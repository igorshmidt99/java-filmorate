package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.module.Components.Film;
import ru.yandex.practicum.filmorate.module.Exceptions.Exist.FilmExistException;

import java.util.List;

public interface FilmStorage {
    Film add(Film film) throws FilmExistException;
    Film update(Film film) throws FilmExistException;
    Film delete(Film film);
    List<Film> getFilms();
}