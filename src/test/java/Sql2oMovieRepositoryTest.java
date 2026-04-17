import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.Sql2oMovieRepository;
import ru.job4j.cinema.repository.Sql2oFileRepository;
import java.util.List;
import java.util.Properties;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oMovieRepositoryTest {

    private static Sql2oMovieRepository sql2oMovieRepository;
    private static Sql2oFileRepository sql2oFileRepository;
    private static File file;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oMovieRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oMovieRepository = new Sql2oMovieRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);

        //we create at least one file as soon as movie depends on file
        file = new File("test", "test");
        sql2oFileRepository.save(file);
    }

    @AfterAll
    public static void deleteFile() {
        sql2oFileRepository.deleteById(file.getId());
    }

    @AfterEach
    public void clearMovies() {
        var movies = sql2oMovieRepository.findAll();
        for (var movie : movies) {
            sql2oMovieRepository.deleteById(movie.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var movie = sql2oMovieRepository.save(
                new Movie.Builder()
                        .id(0)
                        .name("name")
                        .description("description")
                        .year(1990)
                        .fileId(file.getId())
                        .minimalAge(6)
                        .durationInMinutes(120)
                        .genreId(1)
                        .build()
        );
        var savedMovie = sql2oMovieRepository.findById(movie.getId()).get();
        assertThat(savedMovie).usingRecursiveComparison().isEqualTo(movie);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var movie1 = sql2oMovieRepository.save(
                new Movie.Builder().id(0).name("name").description("description").year(1990).fileId(file.getId())
                        .minimalAge(6).durationInMinutes(120).genreId(3).build()
        );

        var movie2 = sql2oMovieRepository.save(
                new Movie.Builder().id(1).name("name2").description("description2").year(1992).fileId(file.getId())
                        .minimalAge(7).durationInMinutes(122).genreId(1).build()
        );

        var movie3 = sql2oMovieRepository.save(
                new Movie.Builder().id(2).name("name3").description("description3").year(1993).fileId(file.getId())
                        .minimalAge(8).durationInMinutes(123).genreId(2).build()
        );
        var result = sql2oMovieRepository.findAll();
        assertThat(result).isEqualTo(List.of(movie1, movie2, movie3));
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oMovieRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oMovieRepository.findById(0)).isEqualTo(empty());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var movie = sql2oMovieRepository.save(
                new Movie.Builder()
                        .id(0)
                        .name("name")
                        .description("description")
                        .year(1990)
                        .fileId(file.getId())
                        .minimalAge(6)
                        .durationInMinutes(120)
                        .genreId(1)
                        .build()
        );
        var isDeleted = sql2oMovieRepository.deleteById(movie.getId());
        var savedMovie = sql2oMovieRepository.findById(movie.getId());
        assertThat(isDeleted).isTrue();
        assertThat(savedMovie).isEqualTo(empty());
    }

    @Test
    public void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oMovieRepository.deleteById(0)).isFalse();
    }

    @Test
    public void whenUpdateThenGetUpdated() {
        sql2oMovieRepository.save(
                new Movie.Builder()
                        .id(0)
                        .name("name")
                        .description("description")
                        .year(1990)
                        .fileId(file.getId())
                        .minimalAge(6)
                        .durationInMinutes(120)
                        .genreId(1)
                        .build()
        );
        var updatedMovie = sql2oMovieRepository.save(
                new Movie.Builder()
                        .id(0)
                        .name("new name")
                        .description("new description")
                        .year(1990)
                        .fileId(file.getId())
                        .minimalAge(6)
                        .durationInMinutes(120)
                        .genreId(2)
                        .build()
        );
        var isUpdated = sql2oMovieRepository.update(updatedMovie);
        var savedMovie = sql2oMovieRepository.findById(updatedMovie.getId()).get();
        assertThat(isUpdated).isTrue();
        assertThat(savedMovie).usingRecursiveComparison().isEqualTo(updatedMovie);
    }

    @Test
    public void whenUpdateUnExistingMovieThenGetFalse() {
        var movie = new Movie.Builder()
                        .id(0)
                        .name("name")
                        .description("description")
                        .year(1990)
                        .fileId(file.getId())
                        .minimalAge(6)
                        .durationInMinutes(120)
                        .genreId(1)
                        .build();
        var isUpdated = sql2oMovieRepository.update(movie);
        assertThat(isUpdated).isFalse();
    }
}