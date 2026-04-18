import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.controller.MovieController;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.service.GenreService;
import ru.job4j.cinema.service.MovieService;
import org.springframework.mock.web.MockMultipartFile;

public class MovieControllerTest {

    private MovieService movieService;
    private MovieController movieController;
    private GenreService genreService;
    private MultipartFile fileMock;

    @BeforeEach
    public void initServices() {
        movieService = mock(MovieService.class);
        genreService = mock(GenreService.class);
        movieController = new MovieController(movieService, genreService);
        fileMock = new MockMultipartFile("testFile.img", new byte[] {1, 2, 3});
    }

    @Test
    public void whenRequestMovieListPageThenGetPageWithMovies() {
        var model = new ConcurrentModel();
        var movie1 = new Movie.Builder()
                .id(1)
                .name("test1")
                .description("desc1")
                .year(1991)
                .fileId(1)
                .minimalAge(6)
                .durationInMinutes(120)
                .genreId(0)
                .build();
        var movie2 = new Movie.Builder()
                .id(2)
                .name("test2")
                .description("desc2")
                .year(1992)
                .fileId(2)
                .minimalAge(7)
                .durationInMinutes(121)
                .genreId(1)
                .build();
        var expectedMovies = List.of(movie1, movie2);
        when(movieService.findAll()).thenReturn(expectedMovies);

        var view = movieController.getAll(model);
        var actualMovies = model.getAttribute("movies");

        assertThat(view).isEqualTo("movies/list");
        assertThat(actualMovies).isEqualTo(expectedMovies);
    }

    @Test 
    public void whenRequestMovieOneThenGetPageWithMovie() {
        var genre = new Genre(1, "comedy");
        var model = new ConcurrentModel();
        var movie = new Movie.Builder()
                .id(1)
                .name("test1")
                .description("desc1")
                .year(1991)
                .fileId(1)
                .minimalAge(6)
                .durationInMinutes(120)
                .genreId(1)
                .build();
        int id = 1;
        when(movieService.findById(id)).thenReturn(Optional.of(movie));
        when(genreService.findById(movie.getGenreId())).thenReturn(Optional.of(genre));

        var view = movieController.getById(model, id);
        var actualMovie = model.get("movie");
        var actualGenreName = model.get("genreName");

        assertThat(view).isEqualTo("movies/oneRead");
        assertThat(actualMovie).isEqualTo(movie);
        assertThat(actualGenreName).isEqualTo(genre.getName());
    }

    @Test 
    public void whenRequestedMovieIsMissingThrowError() {
        var model = new ConcurrentModel();
        int id = 1;
        
        var view = movieController.getById(model, id);
        var errorMessage = model.get("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(errorMessage).isEqualTo("Movie wasn't found for such ID");
    }
}