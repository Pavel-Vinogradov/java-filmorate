package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserController extends BaseController<User> {

    private final List<User> users = new ArrayList<>();


    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }


    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        modelValidator(user);
        user.setId(idGenerator.incrementAndGet());
        users.add(user);
        logger.info("User создан: {}", user);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        User existingUser = findUserById(user.getId());
        if (existingUser == null) {
            logger.error("User id {} не найден", user.getId());
            throw new UserException("User id " + user.getId() + " не найден");
        }
        existingUser.updateFrom(user);
        logger.info("User обновлен: {}", existingUser);
        return ResponseEntity.ok(existingUser);
    }


    private User findUserById(long id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    void modelValidator(User user) throws ValidationException {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }
}