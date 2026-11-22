package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.user.*;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public CreateUserRespDto createUser(@Valid @RequestBody CreateUserReqDto dto) {
        return userService.createUser(dto);
    }

    @PutMapping
    public UpdateUserRespDto createUser(@Valid @RequestBody UpdateUserReqDto dto) {
        return userService.updateUser(dto);
    }

    @GetMapping
    public List<GetUserRespDto> getAllUsers() {
        return userService.getAllUsers();
    }
}