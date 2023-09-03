package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    List<User> getAllUsers();

    User getUserById(Long id);


    User createUser(User user);

    User updateUser(User user);


    User addFriend(Long userId, Long friendId);


    User removeFriend(Long userId, Long friendId);


    List<User> getMutualFriends(Long id, Long otherId);


    List<User> getFriendsByUserId(Long id);
}
