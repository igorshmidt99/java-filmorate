package ru.yandex.practicum.filmorate;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.dto.user.CreateUserReqDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserReqDtoTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @Test
    void shouldPassValidationForValidDto() {
        CreateUserReqDto dto = createValidDto();

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void shouldFailWhenLoginIsNull() {
        CreateUserReqDto dto = createValidDto();
        dto.setLogin(null);

        assertViolation(dto, "login");
    }

    @Test
    void shouldFailWhenLoginIsEmpty() {
        CreateUserReqDto dto = createValidDto();
        dto.setLogin("");

        assertViolation(dto, "login");
    }

    @Test
    void shouldFailWhenLoginContainsOnlyWhitespace() {
        CreateUserReqDto dto = createValidDto();
        dto.setLogin("   ");

        assertViolation(dto, "login");
    }

    @Test
    void shouldFailWhenLoginContainsSpaces() {
        CreateUserReqDto dto = createValidDto();
        dto.setLogin("john doe");

        assertViolation(dto, "login");
    }

    @Test
    void shouldPassWhenLoginContainsNoWhitespace() {
        CreateUserReqDto dto = createValidDto();
        dto.setLogin("john_doe123");

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void shouldFailWhenEmailIsNull() {
        CreateUserReqDto dto = createValidDto();
        dto.setEmail(null);

        assertViolation(dto, "email");
    }

    @Test
    void shouldFailWhenEmailIsEmpty() {
        CreateUserReqDto dto = createValidDto();
        dto.setEmail("");

        assertViolation(dto, "email");
    }

    @Test
    void shouldFailWhenEmailHasInvalidFormat() {
        CreateUserReqDto dto = createValidDto();
        dto.setEmail("not-an-email");

        assertViolation(dto, "email");
    }

    @Test
    void shouldPassWhenEmailIsValid() {
        CreateUserReqDto dto = createValidDto();
        dto.setEmail("john.doe@example.com");

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void shouldPassWhenBirthdayIsNull() {
        CreateUserReqDto dto = createValidDto();
        dto.setBirthday(null);

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void shouldPassWhenBirthdayIsToday() {
        CreateUserReqDto dto = createValidDto();
        dto.setBirthday(LocalDate.now());

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void shouldPassWhenBirthdayIsInPast() {
        CreateUserReqDto dto = createValidDto();
        dto.setBirthday(LocalDate.now().minusYears(20));

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void shouldFailWhenBirthdayIsInFuture() {
        CreateUserReqDto dto = createValidDto();
        dto.setBirthday(LocalDate.now().plusDays(1));

        assertViolation(dto, "birthday");
    }

    @Test
    void shouldPassWhenNameIsNull() {
        CreateUserReqDto dto = createValidDto();
        dto.setName(null);

        assertTrue(validator.validate(dto).isEmpty());
    }

    private CreateUserReqDto createValidDto() {
        return CreateUserReqDto.builder()
                .login("john_doe")
                .name("John")
                .email("john@example.com")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
    }

    private void assertViolation(CreateUserReqDto dto, String property) {
        assertTrue(
                validator.validate(dto).stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals(property))
        );
    }
}