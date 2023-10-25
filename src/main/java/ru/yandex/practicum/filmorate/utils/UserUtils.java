package ru.yandex.practicum.filmorate.utils;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;

@Slf4j
public class UserUtils {
    public static User userParameters(ResultSet resultSet) {
        try {
            User user = new User();
            user.setId(resultSet.getInt("user_id"));
            user.setEmail(resultSet.getString("email"));
            user.setLogin(resultSet.getString("login"));
            user.setName(resultSet.getString("user_name"));
            user.setBirthday(resultSet.getDate("birthday").toLocalDate());
            return user;
        } catch (Exception e) {
            log.error("Error when mapping user from ResultSet", e);
            throw new RuntimeException(e);
        }
    }
}
