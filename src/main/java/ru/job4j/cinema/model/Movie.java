package ru.job4j.cinema.model;

import java.util.Map;
import java.util.Objects;

public class Movie {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "description", "description",
            "\"year\"", "year",
            "file_id", "fileId",
            "minimal_age", "minimalAge",
            "duration_in_minutes", "durationInMinutes",
            "genre_id", "genreId"
    );

    private int id;
    private String name;
    private String description;
    private int year;
    private int fileId;
    private int minimalAge;
    private int durationInMinutes;
    private int genreId;

    public Movie() {
    }

    public Movie(int id, String name, String description, int year,
                 int fileId, int minimalAge, int durationInMinutes, int genreId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.fileId = fileId;
        this.minimalAge = minimalAge;
        this.durationInMinutes = durationInMinutes;
        this.genreId = genreId;
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

    public int getGenreId() {
        return genreId;
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

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Movie movie = (Movie) object;
        return id == movie.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}