package ru.yandex.practicum.filmorate.service;//Fix - Guava из POM.xml использовалась для
//Sets.intersection, и только там.Заменил на стандартное решение getIntersection с использованием HashSet, guava из POM.xml убрал

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exeptions.UserDoesNotExistException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Validated
public class UserService {

    private long nextId = 1;
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {

        this.userStorage = userStorage;
    }

    public List<User> getUsers() {

        return List.copyOf(userStorage.getUsers().values());
    }

    public User create(@Valid User user) {
        for (User registeredUser : userStorage.getUsers().values()) {
            if (registeredUser.getEmail().equals(user.getEmail())) {
                log.warn("Пользователь с e-mail адресом " + user.getEmail() + " уже зарегистрирован");
                throw new ValidationException();
            }
        }

        user.setId(getNextId());
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        log.info("Добавлен новый пользователь");
        return userStorage.create(user);
    }

    public User update(@Valid User user) {
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

        User user = findUserById(userId);
        User friend = findUserById(friendId);
        if (user != null && friend != null) {

            user.getFriends().add(friendId);
            friend.getFriends().add(userId);
            log.info("Пользователи {} и {} теперь друзья", user, friend);
        }
    }

    public void removeFromFriends(long userId, long friendId) {

        User user = findUserById(userId);
        User friend = findUserById(friendId);
        if (user != null && friend != null) {

            user.getFriends().remove(friend);
            friend.getFriends().remove(user);
            log.info("Пользователи {} и {} теперь не являются друзьями", user, friend);
        }
    }

    public List<User> getMutualFriends(long userId, long otherUserId) {

        List<User> mutualFriends = new ArrayList<>();
        User user = findUserById(userId);
        User otherUser = findUserById(otherUserId);
        Set<Long> mutualFriendsIds = getIntersection(user.getFriends(), otherUser.getFriends());
        for (Long id : mutualFriendsIds) {

            mutualFriends.add(findUserById(id));
        }

        return mutualFriends;
    }

    public List<User> getAllFriends(long userId) {

        List<User> friends = new ArrayList<>();
        User user = findUserById(userId);
        for (Long id : user.getFriends()) {

            friends.add(findUserById(id));
        }
        return friends;
    }

    private long getNextId() {

        return nextId++;
    }

    private Set<Long> getIntersection(Set<Long> set1, Set<Long> set2) {

        Set<Long> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }
}
