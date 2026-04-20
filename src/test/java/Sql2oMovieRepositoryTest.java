import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.movie.Sql2oMovieRepository;
import ru.job4j.cinema.repository.file.Sql2oFileRepository;
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

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oMovieRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oMovieRepository.findById(0)).isEqualTo(empty());
    }
}