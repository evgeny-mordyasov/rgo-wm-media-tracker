package rgo.wm.media.tracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import rgo.wm.media.tracker.tests.Containers;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Import(Containers.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthCheckTest {

    private static final String BASE_URL = "http://localhost:";
    private static final RestTemplate REST = new RestTemplate();

    @LocalServerPort
    private int port;

    @Test
    void health_up() {
        checkUp("/actuator/health");
    }

    @Test
    void livenessProbe_up() {
        checkUp("/actuator/health/liveness");
    }

    @Test
    void readinessProbe_up() {
        checkUp("/actuator/health/readiness");
    }

    @Test
    void db_up() {
        checkUp("/actuator/health/db");
    }

    private void checkUp(String url) {
        StatusV2 status = REST.getForObject(BASE_URL + port + url, StatusV2.class);
        assertThat(status).isNotNull();
        assertThat(status.status()).isEqualTo(Status.UP.getCode());
    }

    private record StatusV2(String status) {
    }
}

