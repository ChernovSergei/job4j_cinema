package ru.job4j.cinema.model;

import java.util.Map;
import java.util.Objects;

public class Hall {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "hall", "hall",
            "description", "description",
            "row_count", "rowCount",
            "place_count", "placeCount"
    );

    private int id;
    private String name;
    private String description;
    private int rowCount;
    private int placeCount;

    public Hall() {
    }

    public Hall(int id, String name, String description, int rowCount, int placeCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rowCount = rowCount;
        this.placeCount = placeCount;
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

    public int getRowCount() {
        return rowCount;
    }

    public int getPlaceCount() {
        return placeCount;
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

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setPlaceCount(int placeCount) {
        this.placeCount = placeCount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Hall hall = (Hall) object;
        return id == hall.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}