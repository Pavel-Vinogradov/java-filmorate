package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseController<T> {

    protected final AtomicLong idGenerator = new AtomicLong(0);

    abstract void modelValidator(T model) throws ValidationException;
}
