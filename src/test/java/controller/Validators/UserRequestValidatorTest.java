package controller.Validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.Validators.UserRequestValidator;
import ru.yandex.practicum.filmorate.controller.Validators.Validator;
import ru.yandex.practicum.filmorate.module.Components.User;
import ru.yandex.practicum.filmorate.module.Exceptions.Invalid.InvalidException;
import ru.yandex.practicum.filmorate.module.Exceptions.Invalid.UserInvalidException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestValidatorTest {

    private static User user;
    private static final Validator<User> validator = new UserRequestValidator();

    @BeforeEach
     void setUp() {
        user = User.builder()
                .name("Igor Shmidt")
                .birthday(LocalDate.of(1999, 4, 7))
                .email("igoremail@gmail.com")
                .id(0)
                .login("igorkiller2010")
                .build();
    }

    @Test
    public void whenEmailIsBlankAndEmailDoesntContainCharacterShouldThrowException() {
        user = user.toBuilder().email("").build();
        InvalidException e = assertThrows(UserInvalidException.class, () -> validator.validate(user));
        assertEquals(e.getMessage(), "Электронная почта не может быть пустой и должна содержать символ @.\n");
        user.toBuilder().email("qwerty").build();
        e = assertThrows(UserInvalidException.class, () -> validator.validate(user));
        assertEquals(e.getMessage(), "Электронная почта не может быть пустой и должна содержать символ @.\n");
    }

    @Test
    public void whenLogInIsEmptyShouldThrowException() {
        user = user.toBuilder().login("").build();
        InvalidException e = assertThrows(UserInvalidException.class, () -> validator.validate(user));
        assertEquals(e.getMessage(), "Логин не может быть пустым и содержать пробелы\n");
        user.toBuilder().login("qwerty ").build();
        e = assertThrows(UserInvalidException.class, () -> validator.validate(user));
        assertEquals(e.getMessage(), "Логин не может быть пустым и содержать пробелы\n");
    }

    @Test
    public void whenBirthdayDateIsInTheFutureShouldThrowException() {
        user = user.toBuilder().birthday(LocalDate.of(2025, 1, 1)).build();
        InvalidException e = assertThrows(UserInvalidException.class, () -> validator.validate(user));
        assertEquals(e.getMessage(), "Дата рождения не может быть в будущем.\n");
    }

    @Test
    public void whenNameIsEmptyFieldNameTakesAValueFromLogin() throws InvalidException {
        user = user.toBuilder().name("").build();
        validator.validate(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void whenUserRequestCollectAllMistakesShouldThrowSpecificMessageWithException() {
        String message = "Электронная почта не может быть пустой и должна содержать символ @.\n" +
                "Логин не может быть пустым и содержать пробелы\n" +
                "Дата рождения не может быть в будущем.\n";
        user = user.toBuilder()
                .birthday(LocalDate.of(2025, 1, 1))
                .email("")
                .login("")
                .build();
        InvalidException e = assertThrows(UserInvalidException.class, () -> validator.validate(user));
        assertEquals(e.getMessage(), message);
    }

    @Test
    public void whenIsNoMistakeDoesntException() {
        assertDoesNotThrow(() -> validator.validate(user));
    }
}