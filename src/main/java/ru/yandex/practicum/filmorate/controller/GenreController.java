package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> allGenres() {
        log.info("List of movie genre");
        return genreService.getGenres();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Genre getGenreById(@PathVariable Integer id) {
        log.info("Get genre by id=" + id);
        return genreService.getGenreById(id);
    }
}
