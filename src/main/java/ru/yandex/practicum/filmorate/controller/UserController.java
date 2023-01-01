package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        List<User> users = userStorage.getUsers();
        log.info("Список пользователей в размере {} передан", users.size());
        return users;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws UserExistException {
        try {
            user = userStorage.add(user);
            if (user == null) throw new UserExistException("Пользователь с таким email уже существует!");
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
            user = userStorage.update(user);
            if (user == null) throw new UserExistException("Этого пользователя не существует.");
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
        try {
            User user = userService.removeFriend(id, friendId);
            log.info("Пользователь с id {} удален.", id);
            return user;
        } catch (UserExistException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(
            @PathVariable long id,
            @PathVariable long otherId
    ) throws UserExistException {
        try {
            List<User> u = userService.getMutualFriends(id, otherId);
            log.info("Список общих друзей пользователя id #{} с пользователем id #{} передан.", id, otherId);
            return u;
        } catch (UserExistException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable long id) throws UserExistException {
        try {
            List<User> u = userService.getAllFriends(id);
            log.info("Список друзей пользователя id #{} передан.", id);
            return u;
        } catch (UserExistException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) throws UserExistException {
        try {
            User user = userStorage.getById(id);
            if (user == null) throw new UserExistException("Этого пользователя не существует.");
            log.info("Пользователь с id {} передан", id);
            return user;
        } catch (UserExistException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}