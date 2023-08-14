package ru.yandex.practicum.filmorate.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/films", produces = "application/json")
public class FilmController extends BaseController<Film> {

    private final List<Film> films = new ArrayList<>();

    @GetMapping
    public List<Film> getFilms() {
        return films;
    }

    @PostMapping
    public ResponseEntity<?> createFilm(@Valid @RequestBody Film film) {
        modelValidator(film);
        film.setId(idGenerator.incrementAndGet());
        films.add(film);
        logger.info("Film создан: {}", film);
        return ResponseEntity.ok(film);
    }

    @PutMapping
    ResponseEntity<Film> updateFilm(@Valid @RequestBody Film updatedFilm) {
        Film existingFilm = findFilmById(updatedFilm.getId());
        if (existingFilm == null) {
            logger.error("Film id {} не найден", updatedFilm.getId());
            throw new FilmException("Film id " + updatedFilm.getId() + " не найден");
        }
        modelValidator(existingFilm);
        existingFilm.updateFrom(updatedFilm);
        logger.info("Film обновлен: {}", existingFilm);
        return ResponseEntity.ok(existingFilm);
    }


    public Film findFilmById(long id) {
        return films.stream()
                .filter(film -> film.getId() == id)
                .findFirst()
                .orElse(null);
    }


    @Override
    void modelValidator(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            logger.warn("Некорректно указана дата релиза");
            throw new ValidationException("Некорректно указана дата релиза.");

        }
    }
}
