package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = "application/json")
final class UserController extends BaseController<User> {

    private final HashMap<Long, User> users = new HashMap<>();



    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }


    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        modelValidator(user);
        user.setId(idGenerator.incrementAndGet());
        users.put(user.getId(), user);
        log.info("User создан: {}", user);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {

        if (!users.containsKey(user.getId())) {
            log.error("User id {} не найден", user.getId());
            throw new UserException("User id " + user.getId() + " не найден");
        }
        modelValidator(user);
        users.put(user.getId(), user);
        log.info("User обновлен: {}", user);
        return ResponseEntity.ok(user);
    }




    @Override
    void modelValidator(User user) throws ValidationException {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }
}