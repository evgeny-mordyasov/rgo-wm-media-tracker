package rgo.wm.media.tracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.media.tracker.service.api.GenreDto;
import rgo.wm.media.tracker.service.api.GenreService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.wm.common.test.utils.random.StringRandom.randomUUIDAsString;

class GenreRestControllerTest extends AbstractTest {

    @Autowired private GenreService service;

    // TODO:
//    @Test
//    void findAll() {
//        CLIENT.get()
//                .uri(url())
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("status.code").isEqualTo(HttpResponse.SUCCESSFUL_STATUS.code())
//                .jsonPath("media").isNotEmpty()
//                .jsonPath("media[0].uuid").isEqualTo(saved.getUuid().toString())
//                .jsonPath("media[0].name").isEqualTo(saved.getName())
//                .jsonPath("media[0].year").isEqualTo(saved.getYear());
//    }

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
        String fakeUuid = randomUUIDAsString();

        CLIENT.get()
                .uri(url() + "/" + fakeUuid)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status.code").isEqualTo(HttpResponse.NOT_FOUND_STATUS.code());
    }

    @Test
    void findByUuid() {
        List<GenreDto> genres = service.findAll();
        assertThat(genres).isNotEmpty();

        GenreDto genre = genres.get(0);
        assert genre.getUuid() != null;
        CLIENT.get()
                .uri(url() + "/" + genre.getUuid())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("status.code").isEqualTo(HttpResponse.SUCCESSFUL_STATUS.code())
                .jsonPath("genre.uuid").isEqualTo(genre.getUuid().toString())
                .jsonPath("genre.name").isEqualTo(genre.getName())
                .jsonPath("genre.description").isEqualTo(genre.getDescription());
    }

    private String url() {
        return baseUrl() + "/api/v1/genres";
    }
}
