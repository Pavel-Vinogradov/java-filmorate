package ru.yandex.practicum.filmorate.contoller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmController {

    private final List<Film> films = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(FilmController.class);

    @GetMapping("/films")
    List<Film> getFilms() {
        return films;
    }

    @PostMapping("/films/add")
    Film addFilm(@Valid @RequestBody Film film) {
        films.add(film);
        logger.info("Film создан: {}", film);

        return films.get(films.size() - 1);
    }

    @PutMapping("/films/{id}")
    ResponseEntity<Film> updateFilm(@PathVariable long id, @Valid @RequestBody Film updatedFilm) {
        Film existingFilm = findFilmById(id);

        if (existingFilm == null) {
            logger.warn("Film id {} not found", id);

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