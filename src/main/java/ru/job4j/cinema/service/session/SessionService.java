package ru.job4j.cinema.service.session;

import ru.job4j.cinema.model.Session;
import java.util.Collection;
import java.util.Optional;

public interface SessionService {

    Optional<Session> findById(int id);

    Collection<Session> findAll();
}
