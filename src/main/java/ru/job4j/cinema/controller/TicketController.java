package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.service.UserService;

@Controller
@RequestMapping("/tickets")
@ThreadSafe
public class TicketController {
    private final TicketService ticketService;
    private final SessionService sessionService;
    private final UserService userService;

    public TicketController(TicketService ticketService, SessionService sessionService, UserService userService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tickets", ticketService.findAll());
        return "tickets/list";
    }

    @GetMapping("/buy")
    public String getCreationPage(Model model) {
        model.addAttribute("sessions", sessionService.findAll());
        return "tickets/buy";
    }

    @PostMapping("/buy")
    public String create(@ModelAttribute Ticket ticket, Model model) {
        try {
            ticketService.buyTicket(ticket);
            return "sessions/list";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var ticketOptional = ticketService.findTicket(id);
        if (ticketOptional.isEmpty()) {
            model.addAttribute("message", "Ticket wasn't found for such ID");
            return "errors/404";
        }
        model.addAttribute("ticket", ticketOptional.get());
        return "tickets/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Ticket ticket, Model model) {
        try {
            var isUpdate = ticketService.updateTicket(ticket);
            if (!isUpdate) {
                model.addAttribute("message", "Ticket wasn't found for such ID");
                return "errors/404";
            }
            return "redirect:/tickets";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = ticketService.reimburseTicket(id);
        if (!isDeleted) {
            model.addAttribute("message", "Ticket wasn't found for such ID");
            return "errors/404";
        }
        return "redirect:/tickets";
    }
}