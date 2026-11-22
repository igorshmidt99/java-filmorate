package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;

    public void normalizeName() {
        if (name == null || name.isBlank()) {
            name = login;
        }
    }
}
