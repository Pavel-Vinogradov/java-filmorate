package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/mpa")
public class MpaController {
     private final MpaService mpaService;

    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public List<Mpa> getMpa() {
        log.info("List of mpa");
        return mpaService.getMpa();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Mpa getMpaById(@PathVariable Integer id) {
        log.info("Get mpa by id=" + id);
        return mpaService.getMpaById(id);
    }
}