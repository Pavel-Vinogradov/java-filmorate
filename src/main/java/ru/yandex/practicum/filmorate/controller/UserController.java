package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;

    }


    @GetMapping
    public List<User> findAllUsers() {

        return userService.findAllUsers();

    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        log.info("Get information about user by id=" + id);
        return userService.findById(id);
    }

    @GetMapping("/{id}/friends")
    @ResponseBody
    public List<User> getFriends(@PathVariable Integer id) {
        log.info("List of friends user id=" + id);
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseBody
    public List<User> commonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Common friends");
        return userService.commonFriends(id, otherId);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Create user");
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Update user");
        return userService.updateUser(user);
    }


    @PutMapping("/{id}/friends/{friendId}")
    public void addFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Add friend");
        userService.addFriends(id, friendId);
    }


    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseBody
    public void deleteFriendById(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Delete friends by id");
        userService.deleteFriendById(id, friendId);
    }

}