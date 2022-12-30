package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.module.component.User;
import ru.yandex.practicum.filmorate.module.exception.Exist.UserExistException;

import java.util.List;

public interface UserStorage {
    User add(User user) throws UserExistException;
    User delete(User user) throws UserExistException;
    User update(User user) throws UserExistException;
    List<User> getUsers();
    User getById(long id) throws UserExistException;
}