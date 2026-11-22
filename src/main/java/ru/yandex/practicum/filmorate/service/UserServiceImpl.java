package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.user.GetUserRespDto;
import ru.yandex.practicum.filmorate.dto.user.CreateUserReqDto;
import ru.yandex.practicum.filmorate.dto.user.CreateUserRespDto;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserReqDto;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRespDto;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public CreateUserRespDto createUser(CreateUserReqDto dto) {
        User user = User.builder().login(dto.getLogin()).name(dto.getName())
                .email(dto.getEmail()).birthday(dto.getBirthday()).build();
        user.normalizeName();
        user = userRepository.createUser(user);
        return CreateUserRespDto.builder().id(user.getId()).login(user.getLogin()).name(user.getName())
                .email(user.getEmail()).birthday(user.getBirthday()).build();
    }

    @Override
    public UpdateUserRespDto updateUser(UpdateUserReqDto dto) {
        User user = userRepository.getUser(dto.getId());
        user.setBirthday(dto.getBirthday());
        user.setName(dto.getName());
        user.setLogin(dto.getLogin());
        user.setEmail(dto.getEmail());
        user = userRepository.updateUser(user);
        return UpdateUserRespDto.builder().id(user.getId()).login(user.getLogin()).name(user.getName())
                .email(user.getEmail()).birthday(user.getBirthday()).build();
    }

    @Override
    public List<GetUserRespDto> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        return users.stream()
                .map(user -> GetUserRespDto.builder().id(user.getId()).login(user.getLogin()).name(user.getName())
                        .email(user.getEmail()).birthday(user.getBirthday()).build())
                .toList();
    }
}