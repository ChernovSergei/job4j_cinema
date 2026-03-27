package ru.job4j.cinema.service;

import ru.job4j.cinema.model.City;

import java.util.Collection;

public interface CityService {
    Collection<City> findAll();
}
