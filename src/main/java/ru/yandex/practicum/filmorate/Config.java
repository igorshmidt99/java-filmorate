package ru.yandex.practicum.filmorate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class Config {

    @Bean
    public Map<Long, Film> filmRepository() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, User> userRepository() {
        return new HashMap<>();
    }

    @Bean
    public AtomicLong filmCounter() {
        return new AtomicLong();
    }

    @Bean
    public AtomicLong userCounter() {
        return new AtomicLong();
    }
}
