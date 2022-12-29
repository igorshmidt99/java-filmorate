package ru.yandex.practicum.filmorate.module.validator;

import ru.yandex.practicum.filmorate.module.exception.Invalid.InvalidException;

public interface Validator<T> {
    void validate(T t) throws InvalidException;
}