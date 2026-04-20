import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.controller.SessionController;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.service.hall.HallService;
import ru.job4j.cinema.service.movie.MovieService;
import ru.job4j.cinema.service.session.SessionService;
import java.util.List;
import java.util.Optional;
import java.time.temporal.ChronoUnit;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static java.time.LocalDateTime.now;

public class SessionControllerTest {

    private SessionService sessionService;
    private SessionController sessionController;
    private MovieService movieService;
    private HallService hallService;

    @BeforeEach
    public void initServices() {
        sessionService = mock(SessionService.class);
        movieService = mock(MovieService.class);
        hallService = mock(HallService.class);
        sessionController = new SessionController(sessionService, movieService, hallService);
    }

    @Test
    public void whenRequestSessionListPageThenGetPageWithSessions() {
        var model = new ConcurrentModel();
        var startTime = now().truncatedTo(ChronoUnit.MINUTES);
        var endTime = now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(120);
        var session1 = new Session(0, 0, 0, startTime, endTime, 1500);
        var session2 = new Session(1, 1, 1, startTime, endTime, 2000);
        var expectedSessions = List.of(session1, session2);
        when(sessionService.findAll()).thenReturn(expectedSessions);

        var view = sessionController.getAll(model);
        var actualSessions = model.getAttribute("sessions");

        assertThat(view).isEqualTo("sessions/list");
        assertThat(actualSessions).isEqualTo(expectedSessions);
    }

    @Test
    public void whenRequestSessionCreationPageThenGetSessionCreationPage() {
        var model = new ConcurrentModel();
        var view = sessionController.getCreationPage(model);
        assertThat(view).isEqualTo("sessions/add");
    }

    @Test
    public void whenRequestSessionOneThenGetPageWithSession() {
        var model = new ConcurrentModel();
        Hall hall  = new Hall(1, "VIP", 100, 100, "VIP");
        Movie movie = new Movie(1, "Test", "Test",
                1990, 1, 10, 120, 1);
        var startTime = now().truncatedTo(ChronoUnit.MINUTES);
        var endTime = now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(120);
        var session = new Session(1, 1, 1, startTime, endTime, 1500);
        int id = 1;
        when(movieService.findById(id)).thenReturn(Optional.of(movie));
        when(hallService.findById(id)).thenReturn(Optional.of(hall));
        when(sessionService.findById(id)).thenReturn(Optional.of(session));

        var view = sessionController.getById(model, id);
        var actualSession = model.get("session_movie");

        assertThat(view).isEqualTo("tickets/buy");
        assertThat(actualSession).isEqualTo(session);
    }

    @Test
    public void whenRequestedSessionIsMissingThrowError() {

        var model = new ConcurrentModel();
        int id = 1;

        var view = sessionController.getById(model, id);
        var errorMessage = model.get("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(errorMessage).isEqualTo("Session wasn't found for such ID");
    }
}