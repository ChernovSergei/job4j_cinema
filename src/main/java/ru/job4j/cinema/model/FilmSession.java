package ru.job4j.cinema.model;

import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

public class FilmSession {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "film_id", "filmID",
            "hall_id", "hallID",
            "start_time", "startTime",
            "end_time", "endTime",
            "price", "price"
    );

    private int id;
    private int filmID;
    private int hallID;
    private LocalTime startTime;
    private LocalTime endTime;
    private int price;

    public FilmSession() {
    }

    public FilmSession(int id, int filmID, int hallID, LocalTime startTime, LocalTime endTime, int price) {
        this.id = id;
        this.filmID = filmID;
        this.hallID = hallID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getFilmID() {
        return filmID;
    }

    public int getHallID() {
        return hallID;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public void setHallID(int hallID) {
        this.hallID = hallID;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        FilmSession filmSession = (FilmSession) object;
        return id == filmSession.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
