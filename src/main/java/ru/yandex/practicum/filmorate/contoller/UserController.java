package ru.yandex.practicum.filmorate.contoller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserController {

    private final List<User> users = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return users;
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        users.add(user);
        logger.info("User создан: {}", user);
        return users.get(users.size() - 1);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @Valid @RequestBody User updatedUser) {
        User existingUser = findUserById(id);
        if (existingUser == null) {
            logger.warn("User id {} не найден", id);
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
