package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.model.Movie;

import java.util.Collection;
import java.util.Optional;

public interface GenreService {
    Collection<Genre> findAll();

    Optional<Genre> findById(int id);
}
