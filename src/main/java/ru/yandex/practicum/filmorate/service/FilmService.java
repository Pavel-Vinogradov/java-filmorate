package ru.yandex.practicum.filmorate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    private static final String FILM_BIRTHDAY = "1895-12-28";
    private static final String FILM_NOT_FOUND_MSG = "Film with id=%d not found";
    private static final String USER_NOT_FOUND_MSG = "User with id=%d not found";

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeStorage likeStorage;

    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film create(@NonNull Film film) {
        if (film.getId() != 0) {
            throw new ValidationException("ID must be empty");
        }
        checkFilm(film);
        return filmStorage.create(film);
    }

    public Film updateFilm(@NonNull Film film) {
        if (!filmStorage.containsFilm(film.getId())) {
            throw new NotFoundException("There is no such film in the database");
        }
        checkFilm(film);
        return filmStorage.updateFilm(film);
    }

    public Film getFilmById(@NonNull Integer id) {
        checkFilmExists(id);
        log.info(String.format("Info about film id=%d", id));
        return filmStorage.findById(id);
    }

    public void likeFilm(Integer id, Integer userId) {
        checkFilmExists(id);
        checkUserExists(userId);
        likeStorage.likeFilm(id, userId);
        log.info(String.format("Liked the movie with id=%d by user with id=%d", id, userId));
    }

    public void deleteLike(Integer id, Integer userId) {
        checkId(id);
        checkId(userId);
        checkFilmExists(id);
        checkUserExists(userId);
        likeStorage.deleteLike(id, userId);
        log.info(String.format("Like on movie with id=%d by user with id=%d was deleted", id, userId));
    }

    public List<Film> bestFilms(@NonNull  Integer count) {
        if (count <= 0) {
            throw new ValidationException("Count must be over 0");
        }
        return filmStorage.bestFilms(count);
    }

    private void checkFilm(@NonNull Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.parse(FILM_BIRTHDAY))) {
            throw new ValidationException("Time of release must be after" + FILM_BIRTHDAY);
        }
    }

    private void checkFilmExists(@NonNull Integer id) {
        if (!filmStorage.containsFilm(id)) {
            throw new NotFoundException(String.format(FILM_NOT_FOUND_MSG, id));
        }
    }

    private void checkUserExists(@NonNull Integer userId) {
        if (!userStorage.containsUser(userId)) {
            throw new NotFoundException(String.format(USER_NOT_FOUND_MSG, userId));
        }
    }

    private void checkId(@NonNull Integer id) {
        if (id <= 0) {
            throw new NotFoundException("Id must be over 0");
        }
    }
}
