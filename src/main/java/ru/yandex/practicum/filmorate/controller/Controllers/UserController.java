package ru.yandex.practicum.filmorate.controller.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.module.Components.User;
import ru.yandex.practicum.filmorate.module.ComponentsManager;
import ru.yandex.practicum.filmorate.module.Exceptions.Exist.ExistException;
import ru.yandex.practicum.filmorate.module.Exceptions.Exist.UserExistException;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

import static ru.yandex.practicum.filmorate.module.ComponentsManager.getUsersList;
import static ru.yandex.practicum.filmorate.module.ComponentsStorage.users;

/**
 * Это контроллер, который обрабатывает GET, POST & PUT запросы по пути /films
 * */

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public List<User> getUsers() {
        log.info("Список пользователей в размере {} передан", users.size());
        return getUsersList();
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) throws UserExistException {
        try {
            for (User u : users.values()) {
                if (user.getEmail().equals(u.getEmail()))
                    throw new UserExistException("Пользователь с таким email уже существует!");
            }
            if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
            ComponentsManager.createId(user);
            users.put(user.getId(), user);
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
            if (!users.containsKey(user.getId())) throw new UserExistException("Этого пользователя не существует.");
            users.put(user.getId(), user);
            log.info("Данные пользователя {}, {} обновлены.", user.getName(), user.getEmail());
            return user;
        } catch (ValidationException | ExistException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}