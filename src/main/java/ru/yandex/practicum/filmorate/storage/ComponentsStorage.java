package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.filmorate.module.Components.Film;
import ru.yandex.practicum.filmorate.module.Components.User;

import java.util.HashMap;
import java.util.Map;
/**
 * Это хранилище экзепляров Film и User, зарегестрированные post-запросом по путям /users & /films.
 * <p>
 * В хранилище содержатся счетчики id каждого из классов.
 * <p>
 * Цель создания хранилища - в отделении функциональности от контроллеров, цель которых обрабатывать запросы, а не хранить данные.
 * */
public class ComponentsStorage {
    @Getter
    @Setter
    private static int filmIdCounter;
    @Getter
    @Setter
    private static int userIdCounter;
    public static final Map<Integer, User> users = new HashMap<>();
    public static final Map<Integer, Film> films = new HashMap<>();
}
