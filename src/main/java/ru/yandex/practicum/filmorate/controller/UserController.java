package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users", produces = "application/json")
final class UserController {

    private final UserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        log.info("Поступил запрос на получение списка пользователей.");
        return userService.getAllUsers();
    }


    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        log.info("Поступил запрос на создание пользователя.");
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        log.info("Поступил запрос на обновление пользователя.");
        return ResponseEntity.ok(userService.updateUser(user));
    }


    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<User> addFriend(@PathVariable String id, @PathVariable String friendId) {
        log.info("Поступил запрос на добавления в друзья.");
        return ResponseEntity.ok(userService.addFriend(Long.parseLong(id), Long.parseLong(friendId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        log.info("Поступил запрос на получение пользователя по id.");
        return ResponseEntity.ok(userService.getUserById(Long.parseLong(id)));
    }


    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getFriends(@PathVariable String id) {
        log.info("Поступил запрос на получение списка друзей.");
        return ResponseEntity.ok(userService.getUserFriends(Long.parseLong(id)));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getMutualFriends(@PathVariable String id, @PathVariable String otherId) {
        log.info("Поступил запрос на получения списка общих друзей.");
        return ResponseEntity.ok(userService.getMutualFriends(Long.parseLong(id), Long.parseLong(otherId)));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable String id, @PathVariable String friendId) {
        log.info("Поступил запрос на удаление из друзей.");
        userService.deleteFriend(Long.parseLong(id), Long.parseLong(friendId));
    }

}