package rgo.wm.media.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import rgo.wm.media.tracker.tests.Containers;

@ActiveProfiles("test")
@Import(Containers.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractTest {

    protected static final String BASE_URL = "http://localhost:";
    protected static final RestTemplate REST = new RestTemplate();

    @LocalServerPort
    protected int port;

    @Autowired private JdbcClient jdbc;

    protected String baseUrl() {
        return BASE_URL + port;
    }

    protected void truncateTables() {
        jdbc.sql("TRUNCATE TABLE media").update();
    }
}
