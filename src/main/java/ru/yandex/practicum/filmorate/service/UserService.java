package ru.yandex.practicum.filmorate.service;//Fix - стандартное решение getIntersection с использованием HashSet

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exeptions.UserDoesNotExistException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Validated
public class UserService {

    private long nextId = 1;
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {

        this.userStorage = userStorage;
    }

    public Collection<User> getUsers() {

        return Collections.unmodifiableCollection(userStorage.getUsers().values());
    }

    public User create(@Valid User user) {

        UserValidator.validateUser(user);
        for (User registeredUser : userStorage.getUsers().values()) {
            if (registeredUser.getEmail().equals(user.getEmail())) {
                log.warn("Пользователь с электронной почтой " + user.getEmail()
                        + " уже зарегистрирован");
                throw new ValidationException();
            }
        }
        user.setId(getNextId());
        log.info("Добавлен новый пользователь");
        return userStorage.create(user);
    }

    public User update(@Valid User user) {

        UserValidator.validateUser(user);
        if (userStorage.findUserById(user.getId()) == null) {
            log.warn("Невозможно обновить пользователя");
            throw new UserDoesNotExistException();
        }
        log.info("Пользователь с id {} обновлён", user.getId());
        return userStorage.update(user);
    }

    public User findUserById(long id) {

        User user = userStorage.findUserById(id);
        if (user == null) {
            log.warn("Пользователя с id {} не найдено", id);
            throw new UserDoesNotExistException();
        }
        return user;
    }

    public void addFriend(long userId, long friendId) {

        userStorage.addFriend(userId, friendId);
        log.info("Пользователи с id {} и {} теперь друзья", userId, friendId);
    }

    public void removeFromFriends(long userId, long friendId) {

        userStorage.removeFromFriends(userId, friendId);
        log.info("Пользователи с id {} и {} теперь не являются друзьями", userId, friendId);
    }

    public List<User> getMutualFriends(long userId, long otherUserId) {

        return userStorage.getMutualFriends(userId, otherUserId);
    }

    public List<User> getAllFriends(long userId) {

        return userStorage.getAllFriends(userId);
    }

    private long getNextId() {

        return nextId++;
    }

}
