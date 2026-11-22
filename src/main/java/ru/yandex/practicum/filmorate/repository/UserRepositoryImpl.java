package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final Map<Long, User> userRepository;
    private final AtomicLong userCounter;

    @Override
    public User createUser(User user) {
        user.setId(userCounter.incrementAndGet());
        userRepository.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUser(Long id) {
        User user = new User();
        user.setId(userCounter.incrementAndGet());
        return userRepository.getOrDefault(id, user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.putIfAbsent(user.getId(), user);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.values());
    }
}
