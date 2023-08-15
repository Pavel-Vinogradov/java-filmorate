package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/films", produces = "application/json")
final class FilmController extends BaseController<Film> {

    private final HashMap<Long, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public ResponseEntity<?> createFilm(@Valid @RequestBody Film film) {
        modelValidator(film);
        film.setId(idGenerator.incrementAndGet());
        films.put(film.getId(), film);
        log.info("Film создан: {}", film);
        return ResponseEntity.ok(film);
    }

    @PutMapping
    public  ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            log.error("Film id {} не найден", film.getId());
            throw new FilmException("Film id " + film.getId() + " не найден");
        }
        modelValidator(film);
        films.put(film.getId(), film);
        log.info("Film обновлен: {}", film);
        return ResponseEntity.ok(film);
    }


    @Override
    void modelValidator(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            log.warn("Некорректно указана дата релиза");
            throw new ValidationException("Некорректно указана дата релиза.");

        }
    }

}
