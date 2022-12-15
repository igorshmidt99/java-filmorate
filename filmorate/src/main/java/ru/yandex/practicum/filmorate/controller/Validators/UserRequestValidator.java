package ru.yandex.practicum.filmorate.controller.Validators;

import ru.yandex.practicum.filmorate.module.Exceptions.Invalid.UserInvalidException;
import ru.yandex.practicum.filmorate.module.Components.User;

import java.time.LocalDate;

public class UserRequestValidator implements Validator<User>{
    @Override
    public void validate(User user) throws UserInvalidException {
        StringBuilder error = new StringBuilder();
        if (user.getEmail().isBlank() || !user.getEmail().contains("@"))
            error.append("Электронная почта не может быть пустой и должна содержать символ @.\n");
        if (user.getLogin().isBlank() || user.getLogin().contains(" "))
            error.append("Логин не может быть пустым и содержать пробелы\n");
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
        if (user.getBirthday().isAfter(LocalDate.now()))
            error.append("Дата рождения не может быть в будущем.\n");
        if (!error.toString().isEmpty()) throw new UserInvalidException(error.toString());
    }
}