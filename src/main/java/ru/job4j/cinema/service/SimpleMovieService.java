package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.repository.MovieRepository;
import java.util.Collection;
import java.util.Optional;

@Service
@ThreadSafe
public class SimpleMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final FileService fileService;

    public SimpleMovieService(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    private void saveNewFile(Movie movie, FileDto resume) {
        var file = fileService.save(resume);
        movie.setFileId(file.getId());
    }

    @Override
    public Optional<Movie> findById(int id) {
        return movieRepository.findById(id);
    }

    @Override
    public Collection<Movie> findAll() {
        return movieRepository.findAll();
    }
}