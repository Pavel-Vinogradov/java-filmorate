package ru.yandex.practicum.filmorate;

import javax.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class FilmValidationTests  extends BaseModelTest {


    @Test
    void testValidFilm() {
        Film film = Film.builder().build();
        film.setName("Sample Film");
        film.setDescription("A sample film description");
        film.setReleaseDate(LocalDate.of(1990, 1, 1));
        film.setDuration(120.5f);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size());
    }



    @Test
    void testInvalidName() {
        Film film = Film.builder().build();
        film.setName("");
        film.setDescription("A sample film description");
        film.setReleaseDate(LocalDate.of(1990, 1, 1));
        film.setDuration(120.5f);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        ConstraintViolation<Film> violation = violations.iterator().next();
        assertEquals("Название не может быть пустым", violation.getMessage());
    }


    @Test
    void testInvalidDescription() {
        Film film = Film.builder().build();
        film.setName("Sample Film");
        film.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Phasellus accumsan, lectus sit amet aliquet tincidunt, urna odio fringilla quam, " +
                "vitae vestibulum ipsum nunc sit amet liberoуууууууууууууууууууууууу.");
        film.setReleaseDate(LocalDate.of(1990, 1, 1));
        film.setDuration(120.5f);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        ConstraintViolation<Film> violation = violations.iterator().next();
        assertEquals("Описание не должно превышать 200 символов", violation.getMessage());

    }

    @Test
    void testInvalidDuration() {
        Film film = Film.builder().build();
        film.setName("Sample Film");
        film.setDescription("A sample film description");
        film.setReleaseDate(LocalDate.of(1990, 1, 1));
        film.setDuration(-10.0f);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        ConstraintViolation<Film> violation = violations.iterator().next();
        assertEquals("Продолжительность фильма должна быть положительной", violation.getMessage());
    }

}
