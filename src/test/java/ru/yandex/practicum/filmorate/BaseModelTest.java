package ru.yandex.practicum.filmorate;

import javax.validation.Validation;
import javax.validation.Validator;

 public abstract class BaseModelTest {

    protected final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


}
