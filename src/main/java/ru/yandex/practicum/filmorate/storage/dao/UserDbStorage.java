package ru.yandex.practicum.filmorate.storage.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("userDbStorage")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<Long, User> getUsers() {

        Map<Long, User> users = new HashMap<>();
        String sqlQuery = "SELECT * FROM \"USER\"";
        List<User> usersFromDb = jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(User.class));
        for (User user : usersFromDb) {
            users.put(user.getId(), user);
        }
        return users;
    }

    @Override
    public User create(User user) {

        String sqlQuery = "INSERT INTO \"USER\" (EMAIL, LOGIN, BIRTHDAY, NAME) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getBirthday(), user.getName());
        return findUserById(user.getId());
    }

    @Override
    public User update(User user) {

        String sqlQuery = "UPDATE \"USER\" SET EMAIL = ?, LOGIN = ?, BIRTHDAY = ?, NAME = ? WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getBirthday(), user.getName(), user.getId());
        return findUserById(user.getId());
    }

    @Override
    public User findUserById(long id) {

        String sqlQuery = "SELECT * FROM \"USER\" WHERE USER_ID = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(User.class), id);
    }

    @Override
    public void addFriend(long userId, long friendId) {

        User user = findUserById(userId);
        User friend = findUserById(friendId);
        String sqlQuery = "INSERT INTO FRIENDSHIP (USER_FIRST_ID, USER_SECOND_ID) VALUES (?, ?);";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public void removeFromFriends(long userId, long friendId) {

        String sqlQuery = "DELETE FROM FRIENDSHIP WHERE USER_FIRST_ID = ? AND USER_SECOND_ID = ?;";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> getMutualFriends(long userId, long otherUserId) {

        String sqlQuery = "SELECT U.* FROM \"USER\" AS U " +
                "JOIN FRIENDSHIP AS F ON U.USER_ID = F.USER_SECOND_ID " +
                "WHERE F.USER_FIRST_ID = ? " +
                "INTERSECT " +
                "SELECT U.* FROM \"USER\" AS U " +
                "JOIN FRIENDSHIP AS F ON U.USER_ID = F.USER_SECOND_ID " +
                "WHERE F.USER_FIRST_ID = ?";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(User.class), userId, otherUserId);
    }

    @Override //Замена на JOIN
    public List<User> getAllFriends(long userId) {

        String sqlQuery = "SELECT U.* FROM \"USER\" AS U " +
                "JOIN FRIENDSHIP AS F ON U.USER_ID = F.USER_SECOND_ID " +
                "WHERE F.USER_FIRST_ID = ?";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(User.class), userId);
    }
}
