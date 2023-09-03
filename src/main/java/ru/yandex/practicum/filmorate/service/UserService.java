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


    public User addFriend(Long userId, Long friendId) {
        userStorage.addFriend(userId, friendId);
        return userStorage.getUserById(userId);
    }


    public User deleteFriend(Long userId, Long friendId) {
        userStorage.removeFriend(userId, friendId);
        return userStorage.getUserById(userId);
    }


    public List<User> getUserFriends(Long userId) {
        return userStorage.getFriendsByUserId(userId);
    }


    public List<User> getMutualFriends(Long userId, Long otherId) {
        return userStorage.getMutualFriends(userId, otherId);
    }
}
