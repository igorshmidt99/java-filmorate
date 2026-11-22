package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.user.GetUserRespDto;
import ru.yandex.practicum.filmorate.dto.user.CreateUserReqDto;
import ru.yandex.practicum.filmorate.dto.user.CreateUserRespDto;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserReqDto;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRespDto;

import java.util.List;

public interface UserService {
    CreateUserRespDto createUser(CreateUserReqDto dto);

    UpdateUserRespDto updateUser(UpdateUserReqDto dto);

    List<GetUserRespDto> getAllUsers();
}
