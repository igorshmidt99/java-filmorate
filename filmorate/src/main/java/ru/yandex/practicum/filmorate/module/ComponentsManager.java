package ru.yandex.practicum.filmorate.module;

import ru.yandex.practicum.filmorate.module.Components.Film;
import ru.yandex.practicum.filmorate.module.Components.User;

public class ComponentsManager {
    public static void setId(User user) {
        int id = DataStorage.getUserIdCounter() + 1;
        DataStorage.setUserIdCounter(id);
        user.setId(id);
    }

    public static void setId(Film film) {
        int id = DataStorage.getFilmIdCounter() + 1;
        DataStorage.setFilmIdCounter(id);
        film.setId(id);
    }
}
