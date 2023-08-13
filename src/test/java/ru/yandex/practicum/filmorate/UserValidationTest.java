package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserValidationTest extends BaseTest {
    @Test
    void testValidUser() {
        User user = new User();
        user.setEmail("test@test.ru");
        user.setLogin("Login");
        user.setName("Test");
        user.setBirthDate(new Date());

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());
    }

    @Test
    void testInvalidEmail() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("");
        user.setLogin("login");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Электронная почта не может быть пустой", violation.getMessage());
    }

    @Test
    void testInvalidEmailFormat() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("invalid.email.com");
        user.setLogin("login");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Неверный формат электронной почты", violation.getMessage());
    }

    @Test
    void testInvalidLogin() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("invalid@email.com");
        user.setLogin("  "); // Логин состоит из пробелов

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size()); // Изменили ожидаемое количество нарушений
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Логин не должен содержать пробелы", violation.getMessage());
    }
    @Test
    void testInvalidLoginFormat() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("invalid.email.com");
        user.setLogin("");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(3, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Логин не может быть пустым", violation.getMessage());
    }
    @Test
    void testInvalidBirthDate() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("invalid@email.com");
        user.setLogin("login");
        user.setBirthDate(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow's date

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Дата рождения не может быть в будущем", violation.getMessage());
    }
}
