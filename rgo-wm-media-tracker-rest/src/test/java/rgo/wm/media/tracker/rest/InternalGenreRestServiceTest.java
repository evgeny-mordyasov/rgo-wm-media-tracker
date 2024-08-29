package rgo.wm.media.tracker.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.rest.api.NotFoundHttpResponse;
import rgo.wm.media.tracker.rest.api.request.GenreGetByUuidRequest;
import rgo.wm.media.tracker.rest.api.response.GenreGetByUuidResponse;
import rgo.wm.media.tracker.rest.api.response.GenreGetListResponse;
import rgo.wm.media.tracker.service.api.GenreDto;
import rgo.wm.media.tracker.service.api.GenreService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rgo.wm.media.tracker.test.model.generator.rest.GenreRqData.randomGetByUuidRequest;
import static rgo.wm.media.tracker.test.model.generator.service.GenreDtoData.randomPersistentGenreDto;

class InternalGenreRestServiceTest {

    private InternalGenreRestService restService;
    private GenreService service;

    @BeforeEach
    void setUp() {
        service = mock(GenreService.class);
        restService = new InternalGenreRestService(service);
    }

    @Test
    void findAll_empty() {
        GenreGetListResponse response = (GenreGetListResponse) restService.findAll();
        assertThat(response.status()).isEqualTo(HttpResponse.SUCCESSFUL_STATUS);
        assertThat(response.genres()).isEmpty();
    }

    @Test
    void findAll() {
        GenreDto dto = randomPersistentGenreDto();
        when(service.findAll()).thenReturn(List.of(dto));

        GenreGetListResponse response = (GenreGetListResponse) restService.findAll();

        assertThat(response.status()).isEqualTo(HttpResponse.SUCCESSFUL_STATUS);
        assertThat(response.genres()).isNotEmpty();
        assertThat(response.genres()).contains(dto);
    }

    @Test
    void findByUuid_empty() {
        GenreGetByUuidRequest rq = randomGetByUuidRequest();
        when(service.findByUuid(rq.getUuid())).thenReturn(Optional.empty());

        NotFoundHttpResponse response = (NotFoundHttpResponse) restService.findByUuid(rq);

        assertThat(response.status()).isEqualTo(HttpResponse.NOT_FOUND_STATUS);
    }

    @Test
    void findByUuid() {
        GenreDto dto = randomPersistentGenreDto();
        assert dto.getUuid() != null;
        GenreGetByUuidRequest rq = GenreGetByUuidRequest.of(dto.getUuid().toString());
        when(service.findByUuid(rq.getUuid())).thenReturn(Optional.of(dto));

        GenreGetByUuidResponse response = (GenreGetByUuidResponse) restService.findByUuid(rq);

        assertThat(response.status()).isEqualTo(HttpResponse.SUCCESSFUL_STATUS);
        assertThat(response.genre()).isEqualTo(dto);
    }
}