package rgo.wm.media.tracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import rgo.wm.media.tracker.tests.Containers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import(Containers.class)
class HealthCheckTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void health_up() throws Exception {
        checkUp("/actuator/health");
    }

    @Test
    void livenessProbe_up() throws Exception {
        checkUp("/actuator/health/liveness");
    }

    @Test
    void readinessProbe_up() throws Exception {
        checkUp("/actuator/health/readiness");
    }

    @Test
    void db_up() throws Exception {
        checkUp("/actuator/health/db");
    }

    private void checkUp(String url) throws Exception {
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Status.UP.getCode()));
    }
}
