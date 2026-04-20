package ru.job4j.cinema.repository.ticket;

import net.jcip.annotations.ThreadSafe;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.model.Ticket;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@ThreadSafe
public class Sql2oTicketRepository implements TicketRepository {

    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> buyTicket(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO tickets(session_id, row_number, place_number, user_id)
                    VALUES (:sessionId, :rowNumber, :placeNumber, :userId)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return Optional.of(ticket);
        }  catch (Sql2oException e) {
            log.error(e.getMessage(), e);
            if (isUniqueViolation(e)) {
                return Optional.empty();
            }
            throw new IllegalArgumentException(e);
        }
    }

    private boolean isUniqueViolation(Throwable e) {
        for (Throwable exception = e; exception != null; exception = exception.getCause()) {
            if (exception instanceof PSQLException psqlException && "23505".equals(psqlException.getSQLState())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Ticket> findTicket(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets WHERE id = :id");
            query.addParameter("id", id);
            var ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }
}