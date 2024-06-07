package rgo.wm.media.tracker.rest.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;
import rgo.wm.media.tracker.rest.api.response.MediaGetListResponse;
import rgo.wm.media.tracker.rest.api.response.MediaSaveResponse;
import rgo.wm.media.tracker.service.api.MediaDto;
import rgo.wm.media.tracker.service.api.MediaService;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rgo.wm.common.test.utils.random.StringRandom.randomString;

class MediaRestServiceImplTest {

    private MediaRestServiceImpl restService;
    private MediaService service;

    @BeforeEach
    void setUp() {
        service = mock(MediaService.class);
        restService = new MediaRestServiceImpl(service);
    }

    @Test
    void findAll_empty() {
        MediaGetListResponse response = (MediaGetListResponse) restService.findAll();
        assertThat(response.getStatus()).isEqualTo(HttpResponse.SUCCESSFUL_STATUS);
        assertThat(response.getMedia()).isEmpty();
    }

    @Test
    void findAll() {
        MediaDto media = randomMedia();
        when(service.findAll()).thenReturn(List.of(media));

        MediaGetListResponse response = (MediaGetListResponse) restService.findAll();

        assertThat(response.getStatus()).isEqualTo(HttpResponse.SUCCESSFUL_STATUS);
        assertThat(response.getMedia()).isNotEmpty();
        assertThat(response.getMedia()).contains(media);
    }

    @Test
    void save() {
        MediaSaveRequest rq = randomSaveRequest();
        MediaDto media = MediaDto.builder().setName(rq.getName()).setYear(rq.getYear()).build();
        when(service.save(media)).thenReturn(media);

        MediaSaveResponse response = (MediaSaveResponse) restService.save(rq);

        assertThat(response.getStatus()).isEqualTo(HttpResponse.CREATED_STATUS);
        assertThat(response.getMedia()).isEqualTo(media);
    }

    private static MediaDto randomMedia() {
        return MediaDto.builder()
                .setName(randomString())
                .setYear(randomYear())
                .build();
    }

    private static MediaSaveRequest randomSaveRequest() {
        var rq = new MediaSaveRequest();
        rq.setName(randomString());
        rq.setYear(randomYear());
        return rq;
    }

    private static int randomYear() {
        return ThreadLocalRandom.current().nextInt(1895, Integer.MAX_VALUE);
    }
}