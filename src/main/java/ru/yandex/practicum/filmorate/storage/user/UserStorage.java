package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    List<User> getAllUsers();

    User getUserById(long id);


    User createUser(User user);

    User updateUser(User user);


    User addFriend(long userId, long friendId);


    User removeFriend(long userId, long friendId);


    List<User> getMutualFriends(long id, long otherId);


    List<User> getFriendsByUserId(long id);
}
