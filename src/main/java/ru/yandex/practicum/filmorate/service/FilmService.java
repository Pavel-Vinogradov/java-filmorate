package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(long id) {
        return filmStorage.getFilmById(id);
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public void addLike(long filmId, long userId) {
        Film film = filmStorage.getFilmById(filmId);
        film.getLikes().add(userId);
    }

    public void deleteLike(long userId, long filmId) {
        if (filmStorage.getFilmById(filmId).getLikes().contains(userId)) {
            filmStorage.getFilmById(filmId).getLikes().remove(userId);
        } else {
            throw new NotFoundException("Пользователь не ставил лайк этому фильму.");
        }
    }

    public List<Film> getTopFilms(long count) {
        return filmStorage.getAllFilms().stream().sorted((film1, film2) ->
                        film2.getLikes().size() - film1.getLikes().size())
                .limit(count).collect(Collectors.toList());
    }
}
