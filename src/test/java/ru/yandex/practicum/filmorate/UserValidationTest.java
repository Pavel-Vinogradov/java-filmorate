package ru.yandex.practicum.filmorate;

import javax.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

final  class UserValidationTest extends BaseModelTest {

    @Test
    void testValidUser() {
        User user = User.builder().build();
        user.setEmail("test@test.ru");
        user.setLogin("Login");
        user.setName("Test");
        user.setBirthday(LocalDate.of(1990, 1, 1)); // Примерная дата рождения

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());
    }


    @Test
    void testInvalidEmail() {
        User user = User.builder().build();
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
        User user = User.builder().build();
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
        User user = User.builder().build();
        user.setName("John Doe");
        user.setEmail("invalid@email.com");
        user.setLogin("  "); // Логин состоит из пробелов

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size()); // Изменили ожидаемое количество нарушений
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Логин не может быть пустым", violation.getMessage());
    }

    @Test
    void testInvalidLoginFormat() {
        User user = User.builder().build();
        user.setName("John Doe");
        user.setEmail("invalid@email.com");
        user.setLogin("");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(2, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Логин не может быть пустым", violation.getMessage());
    }

    @Test
    void testInvalidBirthDate() {
        User user = User.builder().build();
        user.setName("John Doe");
        user.setEmail("invalid@email.com");
        user.setLogin("login");
        user.setBirthday(LocalDate.now().plusDays(1)); // Завтрашняя дата

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Дата рождения не может быть в будущем", violation.getMessage());
    }

}
