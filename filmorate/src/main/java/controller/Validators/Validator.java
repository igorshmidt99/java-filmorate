package controller.Validators;

import module.Exceptions.Invalid.InvalidException;

public interface Validator<T> {
    public void valid(T t) throws InvalidException;
}