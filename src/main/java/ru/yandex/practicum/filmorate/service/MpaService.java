package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;

@Service
@Slf4j
public class MpaService {
    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<Mpa> getMpa() {
        return mpaStorage.getMpa();
    }

    public Mpa getMpaById(Integer id) {
        if (!mpaStorage.containsMpa(id)) {
            throw new NotFoundException(String.format("Mpa with id=%d not found", id));
        }
        log.info("Info about genre id=" + id);
        return mpaStorage.getMpaById(id);
    }
}
