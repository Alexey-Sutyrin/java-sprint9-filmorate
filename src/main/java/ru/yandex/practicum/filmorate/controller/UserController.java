package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getUsers() {
        log.info("Handling getUsers request");
        return userService.getUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Handling create request with user: {}", user);
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Handling update request with user: {}", user);
        return userService.update(user);
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable long id) {
        log.info("Handling findUserById request for user with id: {}", id);
        return userService.findUserById(id);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("Handling addFriend request for user with id: {} and friendId: {}", id, friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void removeFromFriends(@PathVariable long id, @PathVariable long friendId) {
        log.info("Handling removeFromFriends request for user with id: {} and friendId: {}", id, friendId);
        userService.removeFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable long id) {
        log.info("Handling getAllFriends request for user with id: {}", id);
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable long id, @PathVariable long otherId) {
        log.info("Handling getMutualFriends request for user with id: {} and otherId: {}", id, otherId);
        return userService.getMutualFriends(id, otherId);
    }
}
