package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

@Repository
public class MpaRepository {

    private final JdbcTemplate jdbcTemplate;

    public MpaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Mpa findMpaFilm(Integer mpaId) {
        return new Mpa(mpaId, jdbcTemplate.queryForObject(
                "SELECT mpa_name FROM mpa where mpa_id = ?",
                String.class, mpaId));
    }
}
