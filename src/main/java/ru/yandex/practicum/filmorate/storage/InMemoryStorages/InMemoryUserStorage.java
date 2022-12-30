package ru.yandex.practicum.filmorate.storage.InMemoryStorages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.module.component.User;
import ru.yandex.practicum.filmorate.module.exception.Exist.UserExistException;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private static int userIdCounter;
    private static final Map<Long, User> users = new HashMap<>();

    /**
     * @param user 
     * @return
     */
    @Override
    public User add(User user) throws UserExistException {
        for (User u : users.values()) {
            if (user.getEmail().equals(u.getEmail()))
                throw new UserExistException("Пользователь с таким email уже существует!");
        }
        if (user.getLogin().contains(" ")) throw new ValidationException("Логин не может содержать пробелы");
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
        createId(user);
        users.put(user.getId(), user);
        return user;
    }

    /**
     * @param user 
     * @return
     */
    @Override
    public User delete(User user) throws UserExistException {
        if (!users.containsKey(user.getId())) throw new UserExistException("Этого пользователя не существует.");
        users.remove(user.getId());
        return user;
    }

    /**
     * @param user 
     * @return
     */
    @Override
    public User update(User user) throws UserExistException {
        if (!users.containsKey(user.getId())) throw new UserExistException("Этого пользователя не существует.");
        users.put(user.getId(), user);
        return user;
    }

    /**
     * @return 
     */
    @Override
    public List<User> getUsers() {
        return List.copyOf(users.values());
    }

    @Override
    public User getById(long id) throws UserExistException {
        if (!users.containsKey(id)) throw new UserExistException("Этого пользователя не существует.");
        return users.get(id);
    }

    private void createId(User user) {
        user.setId(++userIdCounter);
    }
}