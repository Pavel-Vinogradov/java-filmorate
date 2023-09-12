package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {


    private final UserStorage userStorage;


    public User addFriend(long userId, long friendId) {
        userStorage.addFriend(userId, friendId);
        return userStorage.getUserById(userId);
    }


    public User deleteFriend(long userId, long friendId) {
        userStorage.removeFriend(userId, friendId);
        return userStorage.getUserById(userId);
    }


    public List<User> getUserFriends(long userId) {
        return userStorage.getFriendsByUserId(userId);
    }


    public List<User> getMutualFriends(long userId, long otherId) {
        return userStorage.getMutualFriends(userId, otherId);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User getUserById(long id) {
        return userStorage.getUserById(id);
    }

}
