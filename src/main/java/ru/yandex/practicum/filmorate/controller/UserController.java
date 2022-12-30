package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.module.component.User;
import ru.yandex.practicum.filmorate.module.exception.Exist.ExistException;
import ru.yandex.practicum.filmorate.module.exception.Exist.UserExistException;
import ru.yandex.practicum.filmorate.service.UserService;
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
    private final UserService userService;

    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        List<User> users = userStorage.getUsers();
        log.info("Список пользователей в размере {} передан", users.size());
        return users;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws UserExistException {
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
    public User updateUser(@Valid @RequestBody User user) throws UserExistException {
        try {
            userStorage.update(user);
            log.info("Данные пользователя {}, {} обновлены.", user.getName(), user.getEmail());
            return user;
        } catch (ValidationException | ExistException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addNewFriend(
            @PathVariable long id,
            @PathVariable long friendId
    ) throws UserExistException {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(
            @PathVariable long id,
            @PathVariable long friendId
    ) throws UserExistException {
        return userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(
            @PathVariable long id,
            @PathVariable long otherId
    ) throws UserExistException {
        return userService.getMutualFriends(id, otherId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable long id) throws UserExistException {
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) throws UserExistException {
        return userStorage.getById(id);
    }
}