package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@ThreadSafe
public class SimpleTicketService implements TicketService {

    private final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket buyTicket(Ticket ticket) {
        return ticketRepository.buyTicket(ticket);
    }

    @Override
    public boolean reimburseTicket(int id) {
        return ticketRepository.reimburseTicket(id);
    }

    @Override
    public boolean updateTicket(Ticket ticket) {
        return ticketRepository.updateTicket(ticket);
    }

    @Override
    public Optional<Ticket> findTicket(int id) {
        return ticketRepository.findTicket(id);
    }

    @Override
    public Collection<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}