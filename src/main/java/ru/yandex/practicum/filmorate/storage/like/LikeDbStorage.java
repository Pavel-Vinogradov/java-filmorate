package ru.yandex.practicum.filmorate.storage.like;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.transaction.Transactional;

@Repository
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmStorage filmStorage;

    @Transactional
    @Override
    public void likeFilm(Integer id, Integer userId) {
        jdbcTemplate.update("INSERT INTO likes (film_id, user_id) VALUES (?, ?)", id, userId);
        Film currentFilm = filmStorage.findById(id);
        currentFilm.setRate(currentFilm.getRate() + 1);
        filmStorage.updateFilm(currentFilm);
    }

    @Transactional
    @Override
    public void deleteLike(Integer id, Integer userId) {
        jdbcTemplate.update("DELETE FROM likes WHERE film_id=? and user_id=?", id, userId);
        Film currentFilm = filmStorage.findById(id);
        if (currentFilm.getRate() > 0) {
            currentFilm.setRate(currentFilm.getRate() - 1);
            filmStorage.updateFilm(currentFilm);
        }
    }
}
