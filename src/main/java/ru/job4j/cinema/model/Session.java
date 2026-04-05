package ru.job4j.cinema.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class Session {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "film_id", "movieId",
            "halls_id", "hallId",
            "start_time", "startTime",
            "end_time", "endTime",
            "price", "price"
    );

    private int id;
    private int movieId;
    private int hallId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int price;

    public Session() {
    }

    public Session(int id, int movieId, int hallId, LocalDateTime startTime, LocalDateTime endTime, int price) {
        this.id = id;
        this.movieId = movieId;
        this.hallId = hallId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getHallId() {
        return hallId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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
        Session session = (Session) object;
        return id == session.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
