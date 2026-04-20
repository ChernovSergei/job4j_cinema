package ru.job4j.cinema.service.session;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.session.SessionRepository;
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
    public Optional<Session> findById(int id) {
        return sessionRepository.findById(id);
    }

    @Override
    public Collection<Session> findAll() {
        return sessionRepository.findAll();
    }
}