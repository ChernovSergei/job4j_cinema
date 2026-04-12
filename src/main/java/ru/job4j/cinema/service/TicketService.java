package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;
import java.util.Collection;
import java.util.Optional;

public interface TicketService {

    Ticket buyTicket(Ticket ticket);

    boolean reimburseTicket(int id);

    boolean updateTicket(Ticket ticket);

    Optional<Ticket> findTicket(int id);

    Collection<Ticket> findAll();
}