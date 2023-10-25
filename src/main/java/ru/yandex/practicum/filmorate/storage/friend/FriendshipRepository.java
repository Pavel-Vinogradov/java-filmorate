package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendshipRepository {
    public void addFriends(Integer id, Integer friendId);

    public void deleteFriendsById(Integer id, Integer friendId);

    public List<User> getFriends(Integer id);

    public List<User> commonFriends(Integer id, Integer otherId);
}
