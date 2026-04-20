package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.service.genre.GenreService;
import ru.job4j.cinema.service.movie.MovieService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/movies")
@ThreadSafe
public class MovieController {

    private final MovieService movieService;
    private final GenreService genreService;

    public MovieController(MovieService movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("movies", movieService.findAll());
        return "movies/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var movieOptional = movieService.findById(id);
        if (movieOptional.isEmpty()) {
            model.addAttribute("message", "Movie wasn't found for such ID");
            return "errors/404";
        }
        var genreName = genreService.findById(movieOptional.get().getGenreId()).get().getName();
        model.addAttribute("genreName", genreName);
        model.addAttribute("movie", movieOptional.get());
        return "movies/oneRead";
    }
}