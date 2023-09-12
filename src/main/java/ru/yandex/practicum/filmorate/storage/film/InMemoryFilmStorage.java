package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public final class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else throw new NotFoundException("Фильм не найден");

    }

    @Override
    public Film addFilm(Film film) {
        modelValidator(film);
        film.setId(idGenerator.incrementAndGet());
        film.setLikes(new HashSet<>());
        films.put(film.getId(), film);
        log.info("Film создан: {}", film);
        return film;
    }

    @Override
    public Film updateFilm(Film updatedFilm) {
        Long filmId = updatedFilm.getId();
        if (!films.containsKey(filmId)) {
            log.error("Film id {} не найден", filmId);
            throw new NotFoundException("Film id " + filmId + " не найден");
        }

        Film existingFilm = films.get(filmId);
        updatedFilm.setLikes(existingFilm.getLikes());

        // Производим остальное обновление полей фильма
        modelValidator(updatedFilm);
        films.put(filmId, updatedFilm);
        log.info("Film обновлен: {}", updatedFilm);
        return updatedFilm;
    }

   private void modelValidator(Film film) throws ValidationException {

        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))
                || film.getReleaseDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Некорректно указана дата релиза.");
        }

        if (film.getName().isEmpty()) {
            throw new ValidationException("Некорректно указано название фильма.");
        }

        if (film.getDescription().length() > 200) {
            throw new ValidationException("Превышено количество символов в описании фильма.");
        }

    }
}
