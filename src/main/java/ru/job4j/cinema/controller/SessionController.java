package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.MovieService;
import ru.job4j.cinema.service.SessionService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sessions")
@ThreadSafe
public class SessionController {

    private final SessionService sessionService;
    private final MovieService movieService;
    private final HallService hallService;

    public SessionController(SessionService sessionService, MovieService movieService, HallService hallService) {
        this.sessionService = sessionService;
        this.movieService = movieService;
        this.hallService = hallService;
    }

    @GetMapping
    public String getAll(Model model) {
        List<Movie> movies = movieService.findAll().stream().toList();
        List<Hall> halls = hallService.findAll().stream().toList();
        Map<Integer, Movie> moviesMap = movies.stream().collect(Collectors.toMap(Movie::getId, Function.identity()));
        Map<Integer, Hall> hallsMap = halls.stream().collect(Collectors.toMap(Hall::getId, Function.identity()));
        model.addAttribute("moviesMap", moviesMap);
        model.addAttribute("hallsMap", hallsMap);
        model.addAttribute("sessions", sessionService.findAll());
        return "sessions/list";
    }

    @GetMapping("/add")
    public String getCreationPage(Model model) {
        model.addAttribute("movies", movieService.findAll());
        model.addAttribute("halls", hallService.findAll());
        return "sessions/add";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute Session session, Model model) {
        try {
            sessionService.save(session);
            return "redirect:/sessions";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var sessionOptional = sessionService.findById(id);
        if (sessionOptional.isEmpty()) {
            model.addAttribute("message", "Session wasn't found for such ID");
            return "errors/404";
        }
        model.addAttribute("movies", movieService.findAll());
        model.addAttribute("halls", hallService.findAll());
        model.addAttribute("session_movie", sessionOptional.get());
        return "sessions/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Session session, Model model) {
        try {
            var isUpdate = sessionService.update(session);
            if (!isUpdate) {
                model.addAttribute("message", "Session wasn't found for such ID");
                return "errors/404";
            }
            return "redirect:/sessions";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = sessionService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Session wasn't found for such ID");
            return "errors/404";
        }
        return "redirect:/sessions";
    }
}