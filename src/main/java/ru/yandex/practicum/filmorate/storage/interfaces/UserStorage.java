package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.module.component.User;

import java.util.List;

public interface UserStorage {
    User add(User user);
    User delete(User user);
    User update(User user);
    List<User> getUsers();
    User getById(long id);
}