package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        validateUser(user);
        validateIdNotExist(user.getId());
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        validateUser(user);
        validateIdExist(user.getId());
        return userStorage.updateUser(user);
    }

    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User findById(Integer id) {
        validateIdExist(id);
        return userStorage.findById(id);
    }

    public void addFriends(Integer id, Integer friendId) {
        validateIdExist(id);
        validateIdExist(friendId);
        userStorage.addFriends(id, friendId);
        log.info("Friend added");
    }

    public void deleteFriendsById(Integer id, Integer friendId) {
        validateIdExist(id);
        validateIdExist(friendId);
        userStorage.deleteFriendsById(id, friendId);
    }

    public List<User> getFriends(Integer id) {
        validateIdExist(id);
        log.info("List of friends done");
        return userStorage.getFriends(id);
    }

    public List<User> commonFriends(Integer id, Integer otherId) {
        validateIdExist(id);
        validateIdExist(otherId);
        log.info("List of common friends done");
        return userStorage.commonFriends(id, otherId);
    }

    private void validateIdExist(Integer id) {
        if (!userStorage.containsUser(id)) {
            throw new NotFoundException(String.format("User with id=%d not exist", id));
        }
    }

    private void validateIdNotExist(Integer id) {
        if (id != 0) {
            throw new ValidationException("ID must be empty");
        }
    }

    private void validateUser(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("The login cannot contain spaces");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

}
