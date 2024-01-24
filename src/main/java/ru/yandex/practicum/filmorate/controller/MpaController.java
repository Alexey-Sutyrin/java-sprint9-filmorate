package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/mpa")
@Validated
public class MpaController {

    private final MpaService mpaService;
    @Autowired
    public MpaController(MpaService mpaService) {

        this.mpaService = mpaService;
    }

    @GetMapping
    public Collection<Mpa> getAllMpa() {
        log.info("Handling getAllMpa request");
        return mpaService.getAllMpa();
    }

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable int id) {
        log.info("Handling getMpaById request for Mpa with id: {}", id);
        return mpaService.getMpaById(id);
    }
}
