package ru.job4j.cinema.repository.movie;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Movie;
import java.util.Collection;
import java.util.Optional;

@Repository
@ThreadSafe
public class Sql2oMovieRepository implements MovieRepository {

    private final Sql2o sql2o;

    public Sql2oMovieRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Movie> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films WHERE id = :id");
            query.addParameter("id", id);
            var movie = query.setColumnMappings(Movie.COLUMN_MAPPING).executeAndFetchFirst(Movie.class);
            return Optional.ofNullable(movie);
        }
    }

    @Override
    public Collection<Movie> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films");
            return query.setColumnMappings(Movie.COLUMN_MAPPING).executeAndFetch(Movie.class);
        }
    }
}