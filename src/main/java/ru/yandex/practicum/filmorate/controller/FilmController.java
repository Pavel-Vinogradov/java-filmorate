package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public List<Film> getFilms() {
        return filmService.findAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmsById(@PathVariable Integer id) {
        log.info("Get information about film id=" + id);
        return filmService.getFilmsById(id);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Create new film");
        return filmService.create(film);
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseBody
    public void likeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Like the movie");
        filmService.likeFilm(id, userId);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Rewrite the movie");
        return filmService.rewriteFilm(film);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Delete like the movie");
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    @ResponseBody
    public List<Film> bestFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Show best films");
        return filmService.bestFilms(count);
    }

}

