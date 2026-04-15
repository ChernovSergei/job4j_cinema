package ru.job4j.cinema.model;

import java.util.Map;
import java.util.Objects;

public class Ticket {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "session_id", "sessionId",
            "row_number", "rowNumber",
            "place_number", "placeNumber",
            "user_id", "userId"
    );

    private int id;
    private int sessionId;
    private int rowNumber;
    private int placeNumber;
    private int userId;

    public Ticket() {
    }

    public Ticket(int id, int sessionId, int rowNumber, int placeNumber, int userId) {
        this.id = id;
        this.sessionId = sessionId;
        this.rowNumber = rowNumber;
        this.placeNumber = placeNumber;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) object;
        return id == ticket.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}