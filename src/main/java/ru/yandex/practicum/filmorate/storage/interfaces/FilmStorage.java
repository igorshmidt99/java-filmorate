package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.module.component.Film;

import java.util.List;

public interface FilmStorage {
    Film add(Film film);
    Film update(Film film);
    Film delete(Film film);
    List<Film> getFilms();
    Film getById(long id);
}