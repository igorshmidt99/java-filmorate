package controller.Validators;

import module.Exceptions.Invalid.UserInvalidException;
import module.User;

import java.time.Instant;
import java.time.LocalDate;

public class UserRequestValidator implements Validator<User>{

    @Override
    public void valid(User user) throws UserInvalidException {
        StringBuilder error = new StringBuilder();
        if (user.getEmail().isBlank() || !user.getEmail().contains("@"))
            error.append("Электронная почта не может быть пустой и должна содержать символ @.\n");
        if (user.getLogin().isBlank() || user.getLogin().contains(" "))
            error.append("Логин не может быть пустым и содержать пробелы\n");
        if (user.getName().isBlank())
            user.setName(user.getLogin());
        if (user.getBirthday().isAfter(LocalDate.from(Instant.now())))
            error.append("Дата рождения не может быть в будущем.\n");
        if (error.toString().isEmpty())
            throw new UserInvalidException(error.toString());
    }
}
