package controller.Validators;

import module.Exceptions.Invalid.InvalidException;

public interface Validator<T> {
    public void validate(T t) throws InvalidException;
}