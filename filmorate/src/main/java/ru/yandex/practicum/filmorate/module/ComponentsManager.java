package ru.yandex.practicum.filmorate.module;

import ru.yandex.practicum.filmorate.module.Components.Film;
import ru.yandex.practicum.filmorate.module.Components.User;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.filmorate.module.ComponentsStorage.*;

public class ComponentsManager {
    public static void createId(User user) {
        int id = ComponentsStorage.getUserIdCounter() + 1;
        ComponentsStorage.setUserIdCounter(id);
        user.setId(id);
    }

    public static void createId(Film film) {
        int id = ComponentsStorage.getFilmIdCounter() + 1;
        ComponentsStorage.setFilmIdCounter(id);
        film.setId(id);
    }

    public static List<User> getUsersList() {
        return new ArrayList<>(users.values());
    }

    public static List<Film> getFilmsList() {
        return new ArrayList<>(films.values());
    }

}
