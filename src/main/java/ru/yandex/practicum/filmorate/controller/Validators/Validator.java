package ru.yandex.practicum.filmorate.controller.Validators;

import ru.yandex.practicum.filmorate.controller.Exceptions.Invalid.InvalidException;

public interface Validator<T> {
    void validate(T t) throws InvalidException;
}