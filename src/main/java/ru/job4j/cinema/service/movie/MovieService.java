package ru.job4j.cinema.service.movie;

import ru.job4j.cinema.model.Movie;
import java.util.Collection;
import java.util.Optional;

public interface MovieService {

    Optional<Movie> findById(int id);

    Collection<Movie> findAll();
}
