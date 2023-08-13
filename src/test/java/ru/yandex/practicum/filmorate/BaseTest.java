package ru.yandex.practicum.filmorate;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

abstract public class BaseTest {
    protected final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

}
