package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Movie;
import java.util.Collection;
import java.util.Optional;

public interface MovieRepository {

    Movie save(Movie movie);

    boolean deleteById(int id);

    boolean update(Movie movie);

    Optional<Movie> findById(int id);

    Collection<Movie> findAll();
}
