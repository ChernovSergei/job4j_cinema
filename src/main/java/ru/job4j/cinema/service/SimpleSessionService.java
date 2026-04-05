package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;
import java.util.Collection;
import java.util.Optional;

@Service
@ThreadSafe
public class SimpleSessionService implements SessionService {

    private final SessionRepository sessionRepository;

    public SimpleSessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public boolean deleteById(int id) {
        return sessionRepository.deleteById(id);
    }

    @Override
    public boolean update(Session session) {
        return sessionRepository.update(session);
    }

    @Override
    public Optional<Session> findById(int id) {
        return sessionRepository.findById(id);
    }

    @Override
    public Collection<Session> findAll() {
        return sessionRepository.findAll();
    }
}
