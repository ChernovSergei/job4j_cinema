package ru.job4j.cinema.repository;

import java.util.Collection;
import ru.job4j.cinema.model.City;

public interface CityRepository {
    Collection<City> findAll();
}
