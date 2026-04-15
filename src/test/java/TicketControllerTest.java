import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.controller.TicketController;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.service.UserService;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketControllerTest {

    private TicketService ticketService;
    private TicketController ticketController;
    private SessionService sessionService;
    private UserService userService;

    @BeforeEach
    public void initServices() {
        sessionService = mock(SessionService.class);
        userService = mock(UserService.class);
        ticketService = mock(TicketService.class);
        ticketController = new TicketController(ticketService, sessionService, userService);
    }

    @Test
    public void whenRequestTicketCreationPageThenGetTicketCreationPage() {
        var model = new ConcurrentModel();
        var view = ticketController.getCreationPage(model);
        assertThat(view).isEqualTo("tickets/buy");
    }

    @Test
    public void whenPostTicketWithFileThenSameDataAndRedirectToTicketsPage() throws Exception {
        var model = new ConcurrentModel();
        var ticket = new Ticket(1, 1, 1, 1, 1);
        var ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);
        when(ticketService.buyTicket(ticketArgumentCaptor.capture())).thenReturn(Optional.of(ticket));

        var view = ticketController.create(ticket, model);
        var actualTicket = ticketArgumentCaptor.getValue();

        assertThat(view).isEqualTo("tickets/success");
        assertThat(actualTicket).isEqualTo(ticket);
    }

    @Test
    public void whenSomeExceptionThrownThenGetErrorPageWithMessage() {
        var expectedException = new RuntimeException("Failed to book the ticket");
        when(ticketService.buyTicket(any())).thenThrow(expectedException);

        var model = new ConcurrentModel();
        var view = ticketController.create(new Ticket(), model);
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }

    @Test
    public void whenBuyTheSameTicketTwiceThenGetError() throws Exception {
        var model = new ConcurrentModel();
        var ticket1 = new Ticket(1, 1, 1, 1, 1);
        var ticket2 = new Ticket(1, 1, 1, 1, 1);

        ticketController.create(ticket1, model);
        var view2 = ticketController.create(ticket2, model);
        String errorMessage = model.getAttribute("message").toString();

        assertThat(view2).isEqualTo("errors/404");
        assertThat(errorMessage).isEqualTo("This place is occupied! Choose another seat.");
    }
}
