package rgo.wm.media.tracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.rest.api.InvalidRqHttpResponse;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;
import rgo.wm.media.tracker.rest.api.response.MediaGetListResponse;
import rgo.wm.media.tracker.rest.api.response.MediaSaveResponse;
import rgo.wm.media.tracker.service.api.MediaDto;
import rgo.wm.media.tracker.service.api.MediaService;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.wm.common.test.utils.random.ShortRandom.randomPositiveShort;
import static rgo.wm.common.test.utils.random.StringRandom.randomString;

class MediaRestControllerTest extends AbstractTest {

    @Autowired private MediaService service;

    @BeforeEach
    void setUp() {
        truncateTables();
    }

    @Test
    void findAll_empty() {
        MediaGetListResponse response = REST.getForObject(url(), MediaGetListResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(HttpResponse.SUCCESSFUL_STATUS);
        assertThat(response.media()).isEmpty();
    }

    @Test
    void findAll() {
        MediaDto saved = service.save(randomMediaDto());

        MediaGetListResponse response = REST.getForObject(url(), MediaGetListResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(HttpResponse.SUCCESSFUL_STATUS);
        assertThat(response.media()).isNotEmpty();
        assertThat(response.media()).contains(saved);
    }

    @Test
    void save_nameIsNull() {
        MediaSaveRequest rq = new MediaSaveRequest();
        rq.setName(null);
        rq.setYear(randomPositiveShort());

        InvalidRqHttpResponse response = REST.postForObject(url(), rq, InvalidRqHttpResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(HttpResponse.INVALID_RQ_STATUS);
    }

    @Test
    void save_nameIsEmpty() {
        MediaSaveRequest rq = new MediaSaveRequest();
        rq.setName("");
        rq.setYear(randomPositiveShort());

        InvalidRqHttpResponse response = REST.postForObject(url(), rq, InvalidRqHttpResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(HttpResponse.INVALID_RQ_STATUS);
    }

    @Test
    void save_yearIsInvalid() {
        MediaSaveRequest rq = new MediaSaveRequest();
        rq.setName(randomString());
        rq.setYear(1894);

        InvalidRqHttpResponse response = REST.postForObject(url(), rq, InvalidRqHttpResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(HttpResponse.INVALID_RQ_STATUS);
    }

    @Test
    void save() {
        MediaSaveRequest rq = new MediaSaveRequest();
        rq.setName(randomString());
        rq.setYear(randomYear());

        MediaSaveResponse response = REST.postForObject(url(), rq, MediaSaveResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(HttpResponse.CREATED_STATUS);
        assertThat(response.media().getName()).isEqualTo(rq.getName());
        assertThat(response.media().getYear()).isEqualTo(rq.getYear());
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
