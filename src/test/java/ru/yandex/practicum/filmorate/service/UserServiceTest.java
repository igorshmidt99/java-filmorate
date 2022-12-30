package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.module.component.User;
import ru.yandex.practicum.filmorate.module.exception.Exist.UserExistException;
import ru.yandex.practicum.filmorate.storage.InMemoryStorages.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserServiceTest {

    private final UserStorage storage = new InMemoryUserStorage();
    private final UserService service = new UserService(storage);
    private User client;
    private User friend1;
    private User friend2;
    private List<User> clientsF;
    private List<User> friend1F;
    @BeforeEach
    void setUp() throws UserExistException {
        client = new User(1, "igoremail@gmail.com", "igorkiller2010","Igor Shmidt",
                LocalDate.of(1999, 4, 7));
        friend1 = new User(2, "leha_ubiytsa@gmail.com", "leha228","Alexey Martishev",
                LocalDate.of(1999, 4, 7));
        friend2 = new User(3, "misha@gmail.com", "prostomisha","Misha Mashikiavelli",
                LocalDate.of(1999, 4, 7));
        storage.add(client);
        storage.add(friend1);
        storage.add(friend2);
        service.addFriend(client.getId(), friend1.getId());
        service.addFriend(client.getId(), friend2.getId());
    }

    @AfterEach
    void tearDown() {
        storage.delete(client);
        storage.delete(friend1);
        storage.delete(friend2);
    }

    @Test
    void addFriend() throws UserExistException {
        clientsF = List.of(friend1, friend2);
        friend1F = List.of(client);
        assertEquals(service.getAllFriends(client.getId()), clientsF);
        assertEquals(service.getAllFriends(friend1.getId()), friend1F);
    }

    @Test
    void removeFriend() throws UserExistException {
        clientsF = service.getAllFriends(client.getId());
        service.removeFriend(client.getId(), friend1.getId());
        assertNotEquals(clientsF, service.getAllFriends(client.getId()));

        clientsF = List.of(friend2);
        assertEquals(clientsF, service.getAllFriends(client.getId()));

        friend1F = List.of();
        assertEquals(service.getAllFriends(friend1.getId()), friend1F);
    }

    @Test
    void getMutualFriends() throws UserExistException {
        User user = new User(4, "mishak@gmail.com", "prostomisha","Misha Mashikiavelli",
                LocalDate.of(1999, 4, 7));
        storage.add(user);
        service.addFriend(client.getId(), user.getId());
        service.addFriend(friend1.getId(), friend2.getId());
        service.addFriend(friend1.getId(), user.getId());
        List<User> mutualFriends = List.of(friend2, user);
        assertEquals(service.getMutualFriends(client.getId(), friend1.getId()), mutualFriends);
        storage.delete(user);
    }
}