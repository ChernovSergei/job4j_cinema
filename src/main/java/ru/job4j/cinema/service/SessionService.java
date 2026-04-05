package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;
import java.util.Collection;
import java.util.Optional;

public interface SessionService {

    Session save(Session session);

    boolean deleteById(int id);

    boolean update(Session session);

    Optional<Session> findById(int id);

    Collection<Session> findAll();
}
