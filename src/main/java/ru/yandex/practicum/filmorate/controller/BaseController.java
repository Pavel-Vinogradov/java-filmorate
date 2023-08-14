package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseController<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final AtomicLong idGenerator = new AtomicLong(0);

    abstract void modelValidator(T model) throws ValidationException;
}
