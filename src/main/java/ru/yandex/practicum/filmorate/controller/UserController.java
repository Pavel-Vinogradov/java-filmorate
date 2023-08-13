package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController extends BaseController {

    private final List<User> users = new ArrayList<>();


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return users;
    }


    @PostMapping("/users")
    public ResponseEntity<?> createUser(@NotNull @Valid @RequestBody User user) {
        user.setId(idGenerator.incrementAndGet());
        users.add(user);
        logger.info("User создан: {}", user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User updatedUser) {
        User existingUser = findUserById(updatedUser.getId());
        if (existingUser == null) {
            logger.warn("User id {} не найден", updatedUser.getId());
            return ResponseEntity.notFound().build();
        }
        existingUser.updateFrom(updatedUser);
        logger.info("User обновлен: {}", existingUser);
        return ResponseEntity.ok(existingUser);
    }


    private User findUserById(long id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

}