package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.Sql2oHallRepository;
import java.util.Collection;

@Service
@ThreadSafe
public class SimpleHallService implements HallService {

    private final Sql2oHallRepository sql2oHallRepository;

    public SimpleHallService(Sql2oHallRepository sql2oHallRepository) {
        this.sql2oHallRepository = sql2oHallRepository;
    }

    @Override
    public Collection<Hall> findAll() {
        return sql2oHallRepository.findAll();
    }
}
