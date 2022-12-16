package ru.yandex.practicum.filmorate.controller.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.Validators.UserRequestValidator;
import ru.yandex.practicum.filmorate.controller.Validators.Validator;
import ru.yandex.practicum.filmorate.module.Components.User;
import ru.yandex.practicum.filmorate.module.ComponentsManager;
import ru.yandex.practicum.filmorate.module.Exceptions.Exist.ExistException;
import ru.yandex.practicum.filmorate.module.Exceptions.Exist.UserExistException;
import ru.yandex.practicum.filmorate.module.Exceptions.Invalid.InvalidException;

import static ru.yandex.practicum.filmorate.module.ComponentsManager.getUsersList;
import static ru.yandex.practicum.filmorate.module.ComponentsStorage.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Validator<User> validator = new UserRequestValidator();

    @GetMapping
    public List<User> getUsers() {
        log.info("Список пользователей в размере {} передан", users.size());
        return getUsersList();
    }

    @PostMapping
    public User postUser(@RequestBody User user) throws UserExistException, InvalidException {
        try {
            for (User u : users.values()) {
                if (user.getEmail().equals(u.getEmail()))
                    throw new UserExistException("Пользователь с таким email уже существует!");
            }
            validator.validate(user);
            ComponentsManager.createId(user);
            users.put(user.getId(), user);
            log.info("Новый пользователь {}, {} добавлен.", user.getName(), user.getEmail());
            return user;
        } catch (InvalidException | ExistException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping
    public User putUser(@RequestBody User user) throws UserExistException, InvalidException {
        try {
            if (!users.containsKey(user.getId())) throw new UserExistException("Этого пользователя не существует.");
            validator.validate(user);
            users.put(user.getId(), user);
            log.info("Данные пользователя {}, {} обновлены.", user.getName(), user.getEmail());
            return user;
        } catch (InvalidException | ExistException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}