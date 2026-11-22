package ru.yandex.practicum.filmorate.dto.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GetUserRespDto {
    private Long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
}
