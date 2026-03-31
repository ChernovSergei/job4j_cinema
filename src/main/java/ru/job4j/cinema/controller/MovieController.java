package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.service.GenreService;
import ru.job4j.cinema.service.MovieService;

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

    @GetMapping("/add")
    public String getCreationPage(Model model) {
        model.addAttribute("genres", genreService.findAll());
        return "movies/add";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute Movie movie, @RequestParam MultipartFile file, Model model) {
        try {
            movieService.save(movie, new FileDto(file.getOriginalFilename(), file.getBytes()));
            return "redirect:/movies";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var movieOptional = movieService.findById(id);
        if (movieOptional.isEmpty()) {
            model.addAttribute("message", "Movie wasn't found for such ID");
            return "errors/404";
        }
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("movie", movieOptional.get());
        return "movies/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Movie movie, @RequestParam MultipartFile file, Model model) {
        try {
            var isUpdate = movieService.update(movie, new FileDto(file.getOriginalFilename(), file.getBytes()));
            if (!isUpdate) {
                model.addAttribute("message", "Movie wasn't found for such ID");
                return "errors/404";
            }
            return "redirect:/movies";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = movieService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Movie wasn't found for such ID");
            return "errors/404";
        }
        return "redirect:/movies";
    }
}
