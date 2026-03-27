package ru.job4j.cinema.model;

import java.util.Map;
import java.util.Objects;

public class Ticket {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "session_id", "sessionID",
            "row_number", "rowNumber",
            "place_number", "placeNumber",
            "user_id", "userID"
    );

    private int id;
    private int sessionID;
    private int rowNumber;
    private int placeNumber;
    private int userID;

    public Ticket() {
    }

    public int getId() {
        return id;
    }

    public int getSessionID() {
        return sessionID;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public int getUserID() {
        return userID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
