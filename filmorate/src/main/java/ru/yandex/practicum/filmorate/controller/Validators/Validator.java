package ru.yandex.practicum.filmorate.controller.Validators;

import ru.yandex.practicum.filmorate.module.Exceptions.Invalid.InvalidException;

public interface Validator<T> {
    public void validate(T t) throws InvalidException;
}