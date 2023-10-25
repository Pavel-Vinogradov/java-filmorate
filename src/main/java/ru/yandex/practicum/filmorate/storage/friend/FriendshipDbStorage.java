package ru.yandex.practicum.filmorate.storage.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.UserUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendshipDbStorage implements FriendshipRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriends(Integer id, Integer friendId) {
        jdbcTemplate.update(
                "INSERT INTO friends (friend_id, user_id, status) VALUES (?, ?, ?)",
                friendId, id, true);
    }

    @Override
    public void deleteFriendsById(Integer id, Integer friendId) {
        jdbcTemplate.update(
                "DELETE FROM friends WHERE friend_id = ? AND user_id = ?",
                friendId, id);
    }

    @Override
    public List<User> getFriends(Integer id) {
        return this.jdbcTemplate.query(
                "SELECT * FROM users WHERE user_id in (SELECT friend_id FROM friends WHERE user_id=?)",
                (resultSet, rowNum) -> UserUtils.userParameters(resultSet), id);
    }

    @Override
    public List<User> commonFriends(Integer id, Integer otherId) {
        List<User> firstId = getFriends(id);
        List<User> secondOtherId = getFriends(otherId);
        firstId.retainAll(secondOtherId);
        return firstId;
    }
}