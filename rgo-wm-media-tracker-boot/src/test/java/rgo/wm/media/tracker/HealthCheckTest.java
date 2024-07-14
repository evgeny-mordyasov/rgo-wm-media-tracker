package rgo.wm.media.tracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Status;

import static org.assertj.core.api.Assertions.assertThat;

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
        StatusV2 status = REST.getForObject(BASE_URL + port + url, StatusV2.class);
        assertThat(status).isNotNull();
        assertThat(status.status()).isEqualTo(Status.UP.getCode());
    }

    private record StatusV2(String status) {
    }
}

