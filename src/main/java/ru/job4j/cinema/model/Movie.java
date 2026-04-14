package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

@Builder
@Getter
@AllArgsConstructor
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

    /*public Movie(int id, String name, String description, int year,
                 int fileId, int minimalAge, int durationInMinutes, int genreId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.fileId = fileId;
        this.minimalAge = minimalAge;
        this.durationInMinutes = durationInMinutes;
        this.genreId = genreId;
    }*/

    private Movie(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.year = builder.year;
        this.fileId = builder.fileId;
        this.minimalAge = builder.minimalAge;
        this.durationInMinutes = builder.durationInMinutes;
        this.genreId = builder.genreId;
    }

    public static class Builder {
        private int id;
        private String name;
        private String description;
        private int year;
        private int fileId;
        private int minimalAge;
        private int durationInMinutes;
        private int genreId;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder fileId(int fileId) {
            this.fileId = fileId;
            return this;
        }

        public Builder minimalAge(int minimalAge) {
            this.minimalAge = minimalAge;
            return this;
        }

        public Builder durationInMinutes(int durationInMinutes) {
            this.durationInMinutes = durationInMinutes;
            return this;
        }

        public Builder genreId(int genreId) {
            this.genreId = genreId;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
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