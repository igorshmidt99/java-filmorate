package ru.yandex.practicum.filmorate.module;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.filmorate.module.Components.Film;
import ru.yandex.practicum.filmorate.module.Components.User;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    @Getter
    @Setter
    private static int filmIdCounter;
    @Getter
    @Setter
    private static int userIdCounter;
    public static final Map<Integer, User> users = new HashMap<>();
    public static final Map<Integer, Film> films = new HashMap<>();
}
