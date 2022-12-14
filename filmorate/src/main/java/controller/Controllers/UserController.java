package controller.Controllers;

import controller.Validators.UserRequestValidator;
import controller.Validators.Validator;
import lombok.extern.slf4j.Slf4j;
import module.Components.User;
import module.Exceptions.Exist.ExistException;
import module.Exceptions.Exist.UserExistException;
import module.Exceptions.Invalid.InvalidException;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static module.Data.DataStorage.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Validator<User> validator = new UserRequestValidator();

    @GetMapping
    public Set<User> getUsers() {
        log.info("Список пользователей в размере {} передан", users.size());
        return users;
    }

    @PostMapping
    public void postUser(@RequestBody User user) {
        try {
            for (User u : users) {
                if (user.getEmail().equals(u.getEmail()))
                    throw new UserExistException("Пользователь с таким email уже существует!");
            }
            validator.validate(user);
            users.add(user);
            log.info("Новый пользователь {}, {} добавлен.", user.getName(), user.getEmail());
        } catch (InvalidException | ExistException e) {
            log.warn(e.getMessage(), e);
        }
    }

    @PutMapping
    public void putUser(@RequestBody User user) {
        try {
            if (!users.contains(user)) throw new UserExistException("Этого пользователя не существует.");
            validator.validate(user);
            users.add(user);
            log.info("Данные пользователя {}, {} обновлены.", user.getName(), user.getEmail());
        } catch (InvalidException | ExistException e) {
            log.warn(e.getMessage(), e);
        }
    }
}