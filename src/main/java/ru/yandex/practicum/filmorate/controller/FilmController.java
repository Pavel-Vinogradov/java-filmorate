package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/films", produces = "application/json")
final class FilmController {
    private final FilmService filmService;

    @GetMapping
    public List<Film> getFilms() {
        log.info("Поступил запрос на получение списка всех фильмов.");
        return filmService.getAllFilms();
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        log.info("Поступил запрос на добавление фильма.");
        return ResponseEntity.ok(filmService.addFilm(film));
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        log.info("Поступил запрос на изменения фильма.");
        return ResponseEntity.ok(filmService.updateFilm(film));
    }


    @PutMapping("/{id}/like/{filmId}")
    public void addLike(@PathVariable String id, @PathVariable String filmId) {
        log.info("Поступил запрос на присвоение лайка фильму.");
        filmService.addLike(Long.parseLong(id), Long.parseLong(filmId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilm(@PathVariable String id) {
        log.info("Получен GET-запрос на получение фильма");
        return ResponseEntity.ok(filmService.getFilmById(Long.parseLong(id)));
    }

    @GetMapping("/popular")
    public List<Film> getBestFilms(@RequestParam(defaultValue = "10") String count) {
        log.info("Поступил запрос на получение списка популярных фильмов.");
        return filmService.getTopFilms(Long.parseLong(count));
    }

    @DeleteMapping("/{id}/like/{filmId}")
    public void deleteLike(@PathVariable String id, @PathVariable String filmId) {
        log.info("Поступил запрос на удаление лайка у фильма.");
        filmService.deleteLike(Long.parseLong(filmId), Long.parseLong(id));
    }

}
