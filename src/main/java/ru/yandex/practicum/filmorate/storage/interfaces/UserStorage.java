package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.module.User;
import ru.yandex.practicum.filmorate.controller.Exceptions.Exist.UserExistException;

import java.util.List;

public interface UserStorage {
    User add(User user) throws UserExistException;
    User delete(User user) throws UserExistException;
    User update(User user) throws UserExistException;
    List<User> getUsers();
}