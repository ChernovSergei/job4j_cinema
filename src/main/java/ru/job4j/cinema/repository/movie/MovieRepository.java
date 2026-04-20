package ru.job4j.cinema.repository.movie;

import ru.job4j.cinema.model.Movie;
import java.util.Collection;
import java.util.Optional;

public interface MovieRepository {

    Optional<Movie> findById(int id);

    Collection<Movie> findAll();
}
