package ru.yandex.practicum.filmorate.dto.film;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateFilmReqDto {
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @NotBlank(message = "Название фильма не должно быть пустым")
    private String name;

    @Size(max = 200, message = "Описание не должно превышать 200 символов")
    private String description;

    @NotNull(message = "Дата релиза обязательна")
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительной")
    private Integer duration;

    @JsonIgnore
    @AssertTrue(message = "Дата релиза не может быть раньше 28.12.1895")
    public boolean isReleaseDateValid() {
        return releaseDate == null || !releaseDate.isBefore(MIN_RELEASE_DATE);
    }
}
