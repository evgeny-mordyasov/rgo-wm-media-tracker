package rgo.wm.media.tracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;
import rgo.wm.media.tracker.service.api.MediaDto;
import rgo.wm.media.tracker.service.api.MediaService;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static rgo.wm.common.test.utils.random.StringRandom.randomString;

class MediaRestControllerTest extends AbstractTest {

    @Autowired private MediaService service;

    @BeforeEach
    void setUp() {
        truncateTables();
    }

    @Test
    void findAll_empty() {
        CLIENT.get()
                .uri(url())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("status.code").isEqualTo(HttpResponse.SUCCESSFUL_STATUS.code())
                .jsonPath("media").isEmpty();
    }

    @Test
    void findAll() {
        MediaDto saved = service.save(randomMediaDto());
        assert saved.getUuid() != null;

        CLIENT.get()
                .uri(url())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("status.code").isEqualTo(HttpResponse.SUCCESSFUL_STATUS.code())
                .jsonPath("media").isNotEmpty()
                .jsonPath("media[0].uuid").isEqualTo(saved.getUuid().toString())
                .jsonPath("media[0].name").isEqualTo(saved.getName())
                .jsonPath("media[0].year").isEqualTo(saved.getYear());
    }

    @Test
    void findByUuid_uuidIsInvalid() {
        String invalidUuid = "abc";

        CLIENT.get()
                .uri(url() + "/" + invalidUuid)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status.code").isEqualTo(HttpResponse.INVALID_RQ_STATUS.code());
    }

    @Test
    void findByUuid_notFound() {
        String fakeUuid = UUID.randomUUID().toString();

        CLIENT.get()
                .uri(url() + "/" + fakeUuid)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status.code").isEqualTo(HttpResponse.NOT_FOUND_STATUS.code());
    }

    @Test
    void findByUuid() {
        MediaDto saved = service.save(randomMediaDto());
        assert saved.getUuid() != null;

        CLIENT.get()
                .uri(url() + "/" + saved.getUuid())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("status.code").isEqualTo(HttpResponse.SUCCESSFUL_STATUS.code())
                .jsonPath("media.uuid").isEqualTo(saved.getUuid().toString())
                .jsonPath("media.name").isEqualTo(saved.getName())
                .jsonPath("media.year").isEqualTo(saved.getYear());
    }

    @Test
    void save_nameIsNull() {
        MediaSaveRequest rq = new MediaSaveRequest();
        rq.setName(null);
        rq.setYear(randomYear());

        CLIENT.post()
                .uri(url())
                .bodyValue(rq)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status.code").isEqualTo(HttpResponse.INVALID_RQ_STATUS.code())
                .jsonPath("errorDetails").isNotEmpty()
                .jsonPath("errorDetails[0].message").isEqualTo("Name must not be null or empty.");
    }

    @Test
    void save_nameIsEmpty() {
        MediaSaveRequest rq = new MediaSaveRequest();
        rq.setName("");
        rq.setYear(randomYear());

        CLIENT.post()
                .uri(url())
                .bodyValue(rq)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status.code").isEqualTo(HttpResponse.INVALID_RQ_STATUS.code())
                .jsonPath("errorDetails").isNotEmpty()
                .jsonPath("errorDetails[0].message").isEqualTo("Name must not be null or empty.");
    }

    @Test
    void save_yearIsInvalid() {
        MediaSaveRequest rq = new MediaSaveRequest();
        rq.setName(randomString());
        rq.setYear(1894);

        CLIENT.post()
                .uri(url())
                .bodyValue(rq)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status.code").isEqualTo(HttpResponse.INVALID_RQ_STATUS.code())
                .jsonPath("errorDetails").isNotEmpty()
                .jsonPath("errorDetails[0].message").isEqualTo("Year must be greater than 1894.");
    }

    @Test
    void save() {
        MediaSaveRequest rq = new MediaSaveRequest();
        rq.setName(randomString());
        rq.setYear(randomYear());

        CLIENT.post()
                .uri(url())
                .bodyValue(rq)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("status.code").isEqualTo(HttpResponse.CREATED_STATUS.code())
                .jsonPath("media.uuid").isNotEmpty()
                .jsonPath("media.name").isEqualTo(rq.getName())
                .jsonPath("media.year").isEqualTo(rq.getYear());
    }

    private String url() {
        return baseUrl() + "/api/v1/media";
    }

    private MediaDto randomMediaDto() {
        return MediaDto.builder()
                .setName(randomString())
                .setYear(randomYear())
                .build();
    }

    private short randomYear() {
        return (short) ThreadLocalRandom.current().nextInt(1895, 2024);
    }
}
