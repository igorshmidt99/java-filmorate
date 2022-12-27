package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.module.Components.Film;

public interface FilmStorage {
    Film add(Film film);
    Film update(Film film);
    Film delete(Film film);
}