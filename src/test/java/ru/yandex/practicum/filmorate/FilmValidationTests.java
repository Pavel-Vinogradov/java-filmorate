package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmValidationTests  extends BaseTest {

    @Test
    void testValidFilm() {
        Film film = new Film();
        film.setName("Sample Film");
        film.setDescription("A sample film description");
        film.setReleaseDate(new Date());
        film.setDuration(120.5f);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size());
    }


    @Test
    void testInvalidName() {
        Film film = new Film();
        film.setName("");
        film.setDescription("A sample film description");
        film.setReleaseDate(new Date());
        film.setDuration(120.5f);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        ConstraintViolation<Film> violation = violations.iterator().next();
        assertEquals("Название не может быть пустым", violation.getMessage());
    }

    @Test
    void testInvalidDescription() {
        Film film = new Film();
        film.setName("Sample Film");
        film.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Phasellus accumsan, lectus sit amet aliquet tincidunt, urna odio fringilla quam, " +
                "vitae vestibulum ipsum nunc sit amet liberoуууууууууууууууууууууууу.");
        film.setReleaseDate(new Date());
        film.setDuration(120.5f);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        ConstraintViolation<Film> violation = violations.iterator().next();
        assertEquals("Описание не должно превышать 200 символов", violation.getMessage());

    }

    @Test
    void testInvalidReleaseDate() {
        Film film = new Film();
        film.setName("Sample Film");
        film.setDescription("A sample film description");
        film.setReleaseDate(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow's date
        film.setDuration(120.5f);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(2, violations.size());
        ConstraintViolation<Film> violation = violations.iterator().next();
        assertEquals("Дата релиза должна быть в прошлом", violation.getMessage());
    }

    @Test
    void testInvalidDuration() {
        Film film = new Film();
        film.setName("Sample Film");
        film.setDescription("A sample film description");
        film.setReleaseDate(new Date());
        film.setDuration(-10.0f);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        ConstraintViolation<Film> violation = violations.iterator().next();
        assertEquals("Продолжительность фильма должна быть положительной", violation.getMessage());
    }

}
