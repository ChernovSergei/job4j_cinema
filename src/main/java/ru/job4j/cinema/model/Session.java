package ru.job4j.cinema.model;

import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

public class Session {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "movie_id", "movieID",
            "hall_id", "hallID",
            "start_time", "startTime",
            "end_time", "endTime",
            "price", "price"
    );

    private int id;
    private int movieID;
    private int hallID;
    private LocalTime startTime;
    private LocalTime endTime;
    private int price;

    public Session() {
    }

    public Session(int id, int movieID, int hallID, LocalTime startTime, LocalTime endTime, int price) {
        this.id = id;
        this.movieID = movieID;
        this.hallID = hallID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getMovieID() {
        return movieID;
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

    public void setMovieID(int movieID) {
        this.movieID = movieID;
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
        Session session = (Session) object;
        return id == session.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
