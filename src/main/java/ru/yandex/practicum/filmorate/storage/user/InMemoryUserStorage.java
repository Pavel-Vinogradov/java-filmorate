package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(Long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else throw new NotFoundException("User not found.");
    }

    @Override
    public User createUser(User user) {
        modelValidator(user);
        user.setFriends(new HashSet<>());
        user.setId(idGenerator.incrementAndGet());
        users.put(user.getId(), user);
        log.info("User создан: {}", user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            log.error("User id {} не найден", user.getId());
            throw new NotFoundException("User id " + user.getId() + " не найден");
        }
        modelValidator(user);
        user.setFriends(new HashSet<>());
        users.put(user.getId(), user);
        log.info("User обновлен: {}", user);
        return user;
    }

    @Override
    public User addFriend(Long userId, Long friendId) {
        getUserById(userId).getFriends().add((friendId));
        getUserById(friendId).getFriends().add((userId));
        return  getUserById(userId);
    }

    @Override
    public User removeFriend(Long userId, Long friendId) {
        getUserById(userId).getFriends().add((friendId));
        getUserById(friendId).getFriends().add((userId));
        return   getUserById(userId);
    }

    @Override
    public List<User> getMutualFriends(Long userId, Long friendId) {
        List<User> mutualFriends = new ArrayList<>();
        for (Long id :getUserById(userId).getFriends()) {
            if (getUserById(friendId).getFriends().contains(id)) {
                mutualFriends.add(getUserById((id)));
            }
        }
        return mutualFriends;
    }

    @Override
    public List<User> getFriendsByUserId(Long id) {
        return getAllUsers().stream()
                .filter(user -> user.getFriends().contains(id))
                .collect(Collectors.toList());
    }

    private void modelValidator(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
