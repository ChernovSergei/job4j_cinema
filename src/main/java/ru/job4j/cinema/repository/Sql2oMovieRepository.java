package ru.job4j.cinema.repository;

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
    public Movie save(Movie movie) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO films(name, description, "year", file_id, genre_id, minimal_age,
                    duration_in_minutes)
                    VALUES (:name, :description, :year, :fileId, :genreId, :minimalAge,
                    :durationInMinutes)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("name", movie.getName())
                    .addParameter("description", movie.getDescription())
                    .addParameter("year", movie.getYear())
                    .addParameter("fileId", movie.getFileId())
                    .addParameter("genreId", movie.getGenreId())
                    .addParameter("minimalAge", movie.getMinimalAge())
                    .addParameter("durationInMinutes", movie.getDurationInMinutes());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            movie.setId(generatedId);
            return movie;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM films WHERE id = :id");
            query.addParameter("id", id);
            var rows = query.executeUpdate().getResult();
            return rows > 0;
        }
    }

    @Override
    public boolean update(Movie movie) {
        try (var connection = sql2o.open()) {
            var sql = """
                    UPDATE films
                    SET name = :name, description = :description, "year" = :year,
                    file_id = :fileId, genre_id = :genreId, minimal_age = :minimalAge,
                    duration_in_minutes = :durationInMinutes
                    WHERE id = :id
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("name", movie.getName())
                    .addParameter("description", movie.getDescription())
                    .addParameter("year", movie.getYear())
                    .addParameter("fileId", movie.getFileId())
                    .addParameter("genreId", movie.getGenreId())
                    .addParameter("minimalAge", movie.getMinimalAge())
                    .addParameter("durationInMinutes", movie.getDurationInMinutes())
                    .addParameter("id", movie.getId());
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
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