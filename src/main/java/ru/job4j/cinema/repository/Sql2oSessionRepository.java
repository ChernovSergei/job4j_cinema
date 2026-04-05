package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Session;

import java.util.Collection;
import java.util.Optional;

@Repository
@ThreadSafe
public class Sql2oSessionRepository implements SessionRepository {

    private final Sql2o sql2o;

    public Sql2oSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Session save(Session session) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
                    VALUES (:filmId, :hallsId, :startTime, :endTime, :price)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("filmId", session.getMovieId())
                    .addParameter("hallsId", session.getHallId())
                    .addParameter("startTime", session.getStartTime())
                    .addParameter("endTime", session.getEndTime())
                    .addParameter("price", session.getPrice());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            session.setId(generatedId);
            return session;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM film_sessions WHERE id = :id");
            query.addParameter("id", id);
            var rows = query.executeUpdate().getResult();
            return rows > 0;
        }
    }

    @Override
    public boolean update(Session session) {
        try (var connection = sql2o.open()) {
            var sql = """
                    UPDATE film_sessions
                    SET film_id = :filmId, halls_id = :hallsId, start_time = :startTime,
                    end_time = :endTime, price = :price
                    WHERE id = :id
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("filmId", session.getMovieId())
                    .addParameter("hallsId", session.getHallId())
                    .addParameter("startTime", session.getStartTime())
                    .addParameter("endTime", session.getEndTime())
                    .addParameter("price", session.getPrice())
                    .addParameter("id", session.getId());
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Optional<Session> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions WHERE id = :id");
            query.addParameter("id", id);
            var session = query.setColumnMappings(Session.COLUMN_MAPPING).executeAndFetchFirst(Session.class);
            return Optional.ofNullable(session);
        }
    }

    @Override
    public Collection<Session> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions");
            return query.setColumnMappings(Session.COLUMN_MAPPING).executeAndFetch(Session.class);
        }
    }
}