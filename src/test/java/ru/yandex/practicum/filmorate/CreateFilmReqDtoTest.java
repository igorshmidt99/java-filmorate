package ru.yandex.practicum.filmorate;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.dto.film.CreateFilmReqDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreateFilmReqDtoTest {

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
        CreateFilmReqDto dto = CreateFilmReqDto.builder()
                .name("Interstellar")
                .description("Science fiction film")
                .releaseDate(LocalDate.of(2014, 11, 7))
                .duration(169)
                .build();

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void shouldFailWhenNameIsNull() {
        CreateFilmReqDto dto = createValidDto();
        dto.setName(null);

        assertViolation(dto, "name");
    }

    @Test
    void shouldFailWhenNameIsEmpty() {
        CreateFilmReqDto dto = createValidDto();
        dto.setName("");

        assertViolation(dto, "name");
    }

    @Test
    void shouldFailWhenNameContainsOnlyWhitespace() {
        CreateFilmReqDto dto = createValidDto();
        dto.setName("   ");

        assertViolation(dto, "name");
    }

    @Test
    void shouldPassWhenDescriptionIsNull() {
        CreateFilmReqDto dto = createValidDto();
        dto.setDescription(null);

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void shouldPassWhenDescriptionLengthIsExactly200() {
        CreateFilmReqDto dto = createValidDto();
        dto.setDescription("a".repeat(200));

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void shouldFailWhenDescriptionLengthIs201() {
        CreateFilmReqDto dto = createValidDto();
        dto.setDescription("a".repeat(201));

        assertViolation(dto, "description");
    }

    @Test
    void shouldFailWhenReleaseDateIsNull() {
        CreateFilmReqDto dto = createValidDto();
        dto.setReleaseDate(null);

        assertViolation(dto, "releaseDate");
    }

    @Test
    void shouldPassWhenReleaseDateIsMinimumAllowed() {
        CreateFilmReqDto dto = createValidDto();
        dto.setReleaseDate(LocalDate.of(1895, 12, 28));

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void shouldFailWhenReleaseDateIsBeforeMinimum() {
        CreateFilmReqDto dto = createValidDto();
        dto.setReleaseDate(LocalDate.of(1895, 12, 27));

        assertViolation(dto, "releaseDateValid");
    }

    @Test
    void shouldPassWhenDurationIsPositive() {
        CreateFilmReqDto dto = createValidDto();
        dto.setDuration(1);

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void shouldFailWhenDurationIsZero() {
        CreateFilmReqDto dto = createValidDto();
        dto.setDuration(0);

        assertViolation(dto, "duration");
    }

    @Test
    void shouldFailWhenDurationIsNegative() {
        CreateFilmReqDto dto = createValidDto();
        dto.setDuration(-1);

        assertViolation(dto, "duration");
    }

    @Test
    void shouldPassWhenDurationIsNull() {
        CreateFilmReqDto dto = createValidDto();
        dto.setDuration(null);

        assertTrue(validator.validate(dto).isEmpty());
    }

    private CreateFilmReqDto createValidDto() {
        return CreateFilmReqDto.builder()
                .name("Interstellar")
                .description("Science fiction film")
                .releaseDate(LocalDate.of(2014, 11, 7))
                .duration(169)
                .build();
    }

    private void assertViolation(CreateFilmReqDto dto, String property) {
        assertTrue(
                validator.validate(dto).stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals(property))
        );
    }
}