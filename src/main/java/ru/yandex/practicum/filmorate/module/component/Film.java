package ru.yandex.practicum.filmorate.module.component;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = "likes")
@ToString(exclude = "likes")
@AllArgsConstructor
@Builder(toBuilder = true)
public class Film {
    private long id;
    @NotEmpty(message = "Название не может быть пустым.")
    @NotNull(message = "Название не может содержать null")
    private final String name;
    @NotNull(message = "Дата релиза не может содержать null")
    private final LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private final int duration;
    @Size(max=200, message = "Максимальная длина описания — 200 символов.")
    @NotNull(message = "Описание не может содержать null")
    private String description;
    private final Set<Long> likes = new HashSet<>();

    public Integer getLikesCount() {
        return likes.size();
    }
}