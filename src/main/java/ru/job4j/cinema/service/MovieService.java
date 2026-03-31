package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.Movie;
import java.util.Collection;
import java.util.Optional;

public interface MovieService {

    Movie save(Movie movie, FileDto resume);

    boolean deleteById(int id);

    boolean update(Movie movie, FileDto resume);

    Optional<Movie> findById(int id);

    Collection<Movie> findAll();
}
