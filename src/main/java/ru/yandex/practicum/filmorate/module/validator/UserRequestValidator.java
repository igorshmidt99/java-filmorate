package ru.yandex.practicum.filmorate.module.validator;

import ru.yandex.practicum.filmorate.module.exception.Invalid.UserInvalidException;
import ru.yandex.practicum.filmorate.module.component.User;

import java.time.LocalDate;
/**
 * Реализует интерфейс Validator.
 * <p>
 *     Метод validate() проверяет поля объекта согласно требованиям:
 *     <p>
 *         email не может быть пустым и должна содержать символ @.
 *         <p>
 *         login не может быть пустым и содержать пробелы.
 *         <p>
 *         birthday не может быть в будущем.
 *         <p>
 *         В случае, если поле name null или пустое, то ему присваивается значение login.
 *         <p>
 *         Если состояние объекта не удовлетворяет требованиям, то в сообщение об ошибке вносится соответствующий текст, который передается в конструктор исключения.
 * */
public class UserRequestValidator implements Validator<User>{
    /**
     * @param user передается c запросом.
     * @throws UserInvalidException в случае неудовлетворения требованиям.
     * */
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