import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.controller.MovieController;
import ru.job4j.cinema.dto.FileDto;
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
        var movie1 = new Movie(1, "test1", "desc1",
                    1990, 1, 6, 120, 0);
        var movie2 = new Movie(2, "test2", "desc2",
                1991, 2, 7, 121, 1);
        var expectedMovies = List.of(movie1, movie2);
        when(movieService.findAll()).thenReturn(expectedMovies);

        var view = movieController.getAll(model);
        var actualMovies = model.getAttribute("movies");

        assertThat(view).isEqualTo("movies/list");
        assertThat(actualMovies).isEqualTo(expectedMovies);
    }

    @Test
    public void whenRequestMovieCreationPageThenGetPageWithCities() {
        var model = new ConcurrentModel();
        var view = movieController.getCreationPage(model);
        assertThat(view).isEqualTo("movies/add");
    }

    @Test   
    public void whenPostMovieWithFileThenSameDataAndRedirectToMoviesPage() throws Exception {
        var model = new ConcurrentModel();
        var movie = new Movie(1, "test1", "desc1",
        1990, 1, 6, 120, 0);
        var fileDto = new FileDto(fileMock.getOriginalFilename(), fileMock.getBytes());
        var movieArgumentCaptor = ArgumentCaptor.forClass(Movie.class);
        var fileDtoArgumentCaptor = ArgumentCaptor.forClass(FileDto.class);
        when(movieService.save(movieArgumentCaptor.capture(), fileDtoArgumentCaptor.capture())).thenReturn(movie);

        var view = movieController.create(movie, fileMock, model);
        var actualMovie = movieArgumentCaptor.getValue();
        var actualFileDto = fileDtoArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/movies");
        assertThat(actualMovie).isEqualTo(movie);
        assertThat(fileDto).usingRecursiveComparison().isEqualTo(actualFileDto);
    }

    @Test
    public void whenSomeExceptionThrownThenGetErrorPageWithMessage() {
        var expectedException = new RuntimeException("Failed to write file");
        when(movieService.save(any(), any())).thenThrow(expectedException);

        var model = new ConcurrentModel();
        var view = movieController.create(new Movie(), fileMock, model);
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }

    @Test 
    public void whenRequestMovieOneThenGetPageWithMovie() {
        var model = new ConcurrentModel();
        var movie = new Movie(1, "test1", "desc1",
        1990, 1, 6, 120, 0);
        int id = 1;
        when(movieService.findById(id)).thenReturn(Optional.of(movie));
        
        var view = movieController.getById(model, id);
        var actualMovie = model.get("movie");

        assertThat(view).isEqualTo("movies/one");
        assertThat(actualMovie).isEqualTo(movie);
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

    @Test   
    public void whenUpdateMovieSuccessfullyThenGetPageWithMovie() throws Exception {
        var movie = new Movie(1, "test1", "desc1",
        1990, 1, 6, 120, 0);
        var fileDto = new FileDto(fileMock.getOriginalFilename(), fileMock.getBytes());
        var model = new ConcurrentModel();
        var movieArgumentCaptor = ArgumentCaptor.forClass(Movie.class);
        var fileDtoArgumentCaptor = ArgumentCaptor.forClass(FileDto.class);
        when(movieService.update(movieArgumentCaptor.capture(), fileDtoArgumentCaptor.capture())).thenReturn(true);

        var view = movieController.update(movie, fileMock, model);
        var actualMovie = movieArgumentCaptor.getValue();
        var actualFileDto = fileDtoArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/movies");
        assertThat(actualMovie).isEqualTo(movie);
        assertThat(fileDto).usingRecursiveComparison().isEqualTo(actualFileDto);
    }

    @Test   
    public void whenUpdateMovieUnsuccessfullyThenErrorMessage() throws Exception {
        var movie = new Movie(1, "test1", "desc1",
        1990, 1, 6, 120, 0);
        var fileDto = new FileDto(fileMock.getOriginalFilename(), fileMock.getBytes());
        var model = new ConcurrentModel();
        var movieArgumentCaptor = ArgumentCaptor.forClass(Movie.class);
        var fileDtoArgumentCaptor = ArgumentCaptor.forClass(FileDto.class);
        when(movieService.update(movieArgumentCaptor.capture(), fileDtoArgumentCaptor.capture())).thenReturn(false);

        var view = movieController.update(movie, fileMock, model);
        var actualMovie = movieArgumentCaptor.getValue();
        var actualFileDto = fileDtoArgumentCaptor.getValue();
        var errorMessage = "Movie wasn't found for such ID";

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMovie).isEqualTo(movie);
        assertThat(fileDto).usingRecursiveComparison().isEqualTo(actualFileDto);
        assertThat(errorMessage).isEqualTo(model.getAttribute("message"));
    }

    @Test
    public void whenUpdateMovieWithErrorThenGetErrorPageWithMessage() {
        var movie = new Movie(1, "test1", "desc1",
        1990, 1, 6, 120, 0);
        var expectedException = new RuntimeException("Failed to update file");
        var model = new ConcurrentModel();
        when(movieService.update(any(), any())).thenThrow(expectedException);

        var view = movieController.update(movie, fileMock, model);
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }

    @Test
    public void whenDeleteMovieThenReturnMoviesPage() throws Exception {
        var model = new ConcurrentModel();
        int id = 1;
        when(movieService.deleteById(id)).thenReturn(true);

        var view = movieController.delete(model, id);

        assertThat(view).isEqualTo("redirect:/movies");
    }

    @Test
    public void whenDeleteMovieUnsuccessfulyThenGetErrors() throws Exception {
        var model = new ConcurrentModel();
        int id = 1;
        var errorMessage = "Movie wasn't found for such ID";
        when(movieService.deleteById(id)).thenReturn(false);

        var view = movieController.delete(model, id);

        assertThat(view).isEqualTo("errors/404");
        assertThat(errorMessage).isEqualTo(model.getAttribute("message"));
    }
}