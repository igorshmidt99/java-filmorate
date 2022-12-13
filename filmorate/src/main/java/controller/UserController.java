package controller;

import controller.Exceptions.Exist.UserExistException;
import lombok.extern.slf4j.Slf4j;
import module.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private static final Set<User> users = new HashSet<>();

    @GetMapping
    public Set<User> getUsers() {
        log.info("Список пользователей в размере {} передан", users.size());
        return users;
    }

    @PostMapping
    public User postUser(@RequestBody User user) throws UserExistException {
        for (User u : users) {
            if (user.getEmail().equals(u.getEmail()))
                throw new UserExistException("Пользователь с таким email уже существует!");
        }
        users.add(user);
        log.info("Пользователь {}, {} добавлен.", user.getName(), user.getEmail());
        return user;
    }

    @PutMapping
    public User putUser(@RequestBody User user) throws UserExistException {
        if (!users.contains(user)) throw new UserExistException("Этого пользователя не существует.");
        users.add(user);
        return user;
    }
}