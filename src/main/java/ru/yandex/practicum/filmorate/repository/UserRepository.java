package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserRepository {
    User createUser(User user);

    User getUser(Long id);

    User updateUser(User user);

    List<User> getAllUsers();

}
