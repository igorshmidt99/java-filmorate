package ru.yandex.practicum.filmorate.controller.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.module.User;
import ru.yandex.practicum.filmorate.controller.Exceptions.Exist.ExistException;
import ru.yandex.practicum.filmorate.controller.Exceptions.Exist.UserExistException;
import ru.yandex.practicum.filmorate.storage.InMemoryStorages.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

/**
 * Это контроллер, который обрабатывает GET, POST & PUT запросы по пути /films
 * */

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserStorage userStorage;
    public UserController(InMemoryUserStorage inMemoryUserStorage) {
        userStorage = inMemoryUserStorage;
    }

    @GetMapping
    public List<User> getUsers() {
        List<User> users = userStorage.getUsers();
        log.info("Список пользователей в размере {} передан", users.size());
        return users;
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) throws UserExistException {
        try {
            userStorage.add(user);
            log.info("Новый пользователь {}, {} добавлен.", user.getName(), user.getEmail());
            return user;
        } catch (ValidationException | ExistException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) throws UserExistException {
        try {
            userStorage.update(user);
            log.info("Данные пользователя {}, {} обновлены.", user.getName(), user.getEmail());
            return user;
        } catch (ValidationException | ExistException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}