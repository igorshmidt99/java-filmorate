package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.module.component.User;
import ru.yandex.practicum.filmorate.module.exception.Exist.UserExistException;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(long clientId, long newFriendId) throws UserExistException {
        User client = userStorage.getById(clientId);
        if (client == null)
            throw new UserExistException(String.format("Пользователя с id %d не существует.", clientId));
        User newFriend = userStorage.getById(newFriendId);
        if (newFriend == null)
            throw new UserExistException(String.format("Пользователя с id %d не существует.", newFriendId));
        if (client.getUsersFriends().contains(newFriend.getId()))
            throw new UserExistException(String.format("Пользователь с id %d уже является другом.", newFriendId));
        client.getUsersFriends().add(newFriend.getId());
        newFriend.getUsersFriends().add(client.getId());
        return newFriend;
    }

    public User removeFriend(long clientId, long removableId) throws UserExistException {
        User client = userStorage.getById(clientId);
        if (client == null)
            throw new UserExistException(String.format("Пользователя с id %d не существует.", clientId));
        User removable = userStorage.getById(removableId);
        if (removable == null)
            throw new UserExistException(String.format("Пользователя с id %d не существует.", removableId));
        if (!client.getUsersFriends().contains(removable.getId()))
            throw new UserExistException(String.format("Пользователя с %d нет в списке друзей.", removable.getId()));
        client.getUsersFriends().remove(removable.getId());
        removable.getUsersFriends().remove(client.getId());
        return removable;
    }

    public List<User> getAllFriends(long clientId) throws UserExistException {
        User user = userStorage.getById(clientId);
        if (user == null) throw new UserExistException(String.format("Пользователя с id %d не существует.", clientId));
        return user.getUsersFriends().stream()
                .map(userStorage::getById)
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriends(long clientId, long friendId) throws UserExistException {
        List<User> clientFriends = getAllFriends(clientId);
        List<User> friendFriends = getAllFriends(friendId);
        return clientFriends.stream()
                .filter(friendFriends::contains)
                .collect(Collectors.toList());
    }
}