package ru.yandex.practicum.filmorate.module.ComponentsManagers;

import ru.yandex.practicum.filmorate.module.Components.Film;
import ru.yandex.practicum.filmorate.module.Components.User;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.filmorate.module.ComponentsManagers.ComponentsStorage.films;
import static ru.yandex.practicum.filmorate.module.ComponentsManagers.ComponentsStorage.users;

/**
 * ComponentsManager манипулирует данными экземпляров User & Film.
 * Для каждого типа компонента есть методы:
 * <p>
 *     createId(T t) - создает новый id объектам Film или Type, учитывая те, что уже присвоены.
 *     <p>
 *     get*Type*List(T t) - преобразует значения из Map в List для сереализации и отправки ответа.
 * */
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
