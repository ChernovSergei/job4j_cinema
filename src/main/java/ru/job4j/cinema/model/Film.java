package ru.job4j.cinema.model;

import java.util.Map;
import java.util.Objects;

public class Film {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "description", "description",
            "\"year\"", "year",
            "file_id", "fileId",
            "minimal_age", "minimalAge",
            "duration_in_minutes", "durationInMinutes",
            "genre_id", "genreID"
    );

    private int id;
    private String name;
    private String description;
    private int year;
    private int fileId;
    private int minimalAge;
    private int durationInMinutes;
    private int genreID;

    public Film() {
    }

    public Film(int id, String name, String description, int year,
                int fileId, int minimalAge, int durationInMinutes, int genreID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.fileId = fileId;
        this.minimalAge = minimalAge;
        this.durationInMinutes = durationInMinutes;
        this.genreID = genreID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getYear() {
        return year;
    }

    public int getFileId() {
        return fileId;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public int getGenreID() {
        return genreID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Film film = (Film) object;
        return id == film.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
