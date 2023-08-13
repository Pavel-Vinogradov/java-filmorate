package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;
@RestController
public class FilmController extends BaseController {
    private final List<Film> films = new ArrayList<>();
    @GetMapping("/films")
    List<Film> getFilms() {
        return films;
    }

    @PostMapping("/film/create")
    public ResponseEntity<?>  createFilm(@NotNull @Valid @RequestBody Film film) {
        film.setId(idGenerator.incrementAndGet());
        films.add(film);
        logger.info("Film создан: {}", film);
        return ResponseEntity.ok(film);
    }

    @PutMapping("/film/{id}")
    ResponseEntity<Film> updateFilm(@PathVariable long id, @Valid @RequestBody Film updatedFilm) {
        Film existingFilm = findFilmById(id);

        if (existingFilm == null) {
            logger.warn("Film id {} не найден", id);
            return ResponseEntity.notFound().build();
        }
        existingFilm.updateFrom(updatedFilm);
        logger.info("Film обновлен: {}", existingFilm);
        return ResponseEntity.ok(existingFilm);
    }

    private Film findFilmById(long id) {
        return films.stream()
                .filter(film -> film.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
