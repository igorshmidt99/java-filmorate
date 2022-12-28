package controller.Validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.Exceptions.Invalid.InvalidException;
import ru.yandex.practicum.filmorate.controller.Exceptions.Invalid.UserInvalidException;
import ru.yandex.practicum.filmorate.controller.Validators.UserRequestValidator;
import ru.yandex.practicum.filmorate.controller.Validators.Validator;
import ru.yandex.practicum.filmorate.module.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestValidatorTest {

    private static User user;
    private static final Validator<User> validator = new UserRequestValidator();

    @BeforeEach
     void setUp() {
        user = new User(0, "leha_ubiytsa@gmail.com", "igorkiller2010","Igor Shmidt",
                LocalDate.of(1999, 4, 7));
    }

    @Test
    public void whenEmailIsBlankAndEmailDoesntContainCharacterShouldThrowException() {
        user.setEmail("");
        InvalidException e = assertThrows(UserInvalidException.class, () -> validator.validate(user));
        assertEquals(e.getMessage(), "Электронная почта не может быть пустой и должна содержать символ @.\n");
        user.setEmail("qwerty");
        e = assertThrows(UserInvalidException.class, () -> validator.validate(user));
        assertEquals(e.getMessage(), "Электронная почта не может быть пустой и должна содержать символ @.\n");
    }

    @Test
    public void whenLogInIsEmptyShouldThrowException() {
        user.setLogin("");
        InvalidException e = assertThrows(UserInvalidException.class, () -> validator.validate(user));
        assertEquals(e.getMessage(), "Логин не может быть пустым и содержать пробелы\n");
        user.setLogin("qwerty ");
        e = assertThrows(UserInvalidException.class, () -> validator.validate(user));
        assertEquals(e.getMessage(), "Логин не может быть пустым и содержать пробелы\n");
    }

    @Test
    public void whenBirthdayDateIsInTheFutureShouldThrowException() {
        user.setBirthday(LocalDate.of(2025, 1, 1));
        InvalidException e = assertThrows(UserInvalidException.class, () -> validator.validate(user));
        assertEquals(e.getMessage(), "Дата рождения не может быть в будущем.\n");
    }

    @Test
    public void whenNameIsEmptyFieldNameTakesAValueFromLogin() throws InvalidException {
        user.setName("");
        validator.validate(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void whenUserRequestCollectAllMistakesShouldThrowSpecificMessageWithException() {
        String message = "Электронная почта не может быть пустой и должна содержать символ @.\n" +
                "Логин не может быть пустым и содержать пробелы\n" +
                "Дата рождения не может быть в будущем.\n";
        user.setBirthday(LocalDate.of(2025, 1, 1));
        user.setEmail("");
        user.setLogin("");
        InvalidException e = assertThrows(UserInvalidException.class, () -> validator.validate(user));
        assertEquals(e.getMessage(), message);
    }

    @Test
    public void whenIsNoMistakeDoesntException() {
        assertDoesNotThrow(() -> validator.validate(user));
    }
}