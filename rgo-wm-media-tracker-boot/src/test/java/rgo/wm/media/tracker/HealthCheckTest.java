package rgo.wm.media.tracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Status;

class HealthCheckTest extends AbstractTest {

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
        CLIENT.get()
                .uri(baseUrl() + url)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("status").isEqualTo(Status.UP.getCode());
    }
}

