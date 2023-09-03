package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class UserControllerTest {
    UserController controller;
    UserStorage userStorage;
    UserService userService;
    User testUser;

    @BeforeEach
    protected void init() {
        userStorage = new InMemoryUserStorage();
        userService = new UserService(userStorage);
        controller = new UserController(userStorage, userService);

        testUser = User.builder()
                .name("John")
                .email("john@mail.ru")
                .login("login")
                .birthday(LocalDate.of(1987, 4, 14))
                .build();

    }

    @Test
    public void createUserNameIsBlankNameIsLoginTest() {
        testUser.setName("");
        controller.createUser(testUser);
        assertEquals("login", controller.getAllUsers().get(0).getName());
    }

    @Test
    void createUserBirthdayInFutureBadRequestTest() {
        testUser.setBirthday(LocalDate.parse("2024-10-12"));
        try {
            controller.createUser(testUser);
        } catch (ValidationException e) {
            assertEquals("Неверно указана дата рождения", e.getMessage());
        }
    }


}