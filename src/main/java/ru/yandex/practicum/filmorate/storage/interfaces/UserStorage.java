package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.module.Components.User;

public interface UserStorage {
    User add(User user);
    User delete(User user);
    User update(User user);
}