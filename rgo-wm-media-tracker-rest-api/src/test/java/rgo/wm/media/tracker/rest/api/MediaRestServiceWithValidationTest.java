package rgo.wm.media.tracker.rest.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rgo.wm.common.utils.rest.api.ErrorDetail;
import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.rest.api.InvalidRqHttpResponse;
import rgo.wm.common.utils.validator.ValidatorAdapter;
import rgo.wm.media.tracker.rest.api.request.MediaGetByUuidRequest;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;
import rgo.wm.media.tracker.rest.api.response.MediaSaveResponse;
import rgo.wm.media.tracker.service.api.MediaDto;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static rgo.wm.common.test.utils.random.StringRandom.randomString;
import static rgo.wm.common.utils.validator.Validators.createValidator;

class MediaRestServiceWithValidationTest {

    private static final ValidatorAdapter VALIDATOR = createValidator();

    private MediaRestService restService;
    private MediaRestService delegate;

    @BeforeEach
    void setUp() {
        delegate = spy(MediaRestService.class);
        restService = delegate.withValidation(VALIDATOR);
    }

    @Test
    void findByUuid_uuidIsInvalid() {
        var rq = new MediaGetByUuidRequest();
        rq.setUuid("abc");

        InvalidRqHttpResponse response = (InvalidRqHttpResponse) restService.findByUuid(rq);

        assertThat(response.status()).isEqualTo(HttpResponse.INVALID_RQ_STATUS);
        assertThat(response.errorDetails())
                .hasSize(1)
                .contains(ErrorDetail.of("UUID not a valid."));
    }

    @Test
    void save_nameIsNull() {
        var rq = new MediaSaveRequest();
        rq.setName(null);
        rq.setYear(randomYearGreaterThan1895());

        InvalidRqHttpResponse response = (InvalidRqHttpResponse) restService.save(rq);

        assertThat(response.status()).isEqualTo(HttpResponse.INVALID_RQ_STATUS);
        assertThat(response.errorDetails())
                .hasSize(1)
                .contains(ErrorDetail.of("Name must not be null or empty."));
    }

    @Test
    void save_yearLessThan1895() {
        var rq = new MediaSaveRequest();
        rq.setName(randomString());
        rq.setYear(randomYearLessThan1895());

        InvalidRqHttpResponse response = (InvalidRqHttpResponse) restService.save(rq);

        assertThat(response.status()).isEqualTo(HttpResponse.INVALID_RQ_STATUS);
        assertThat(response.errorDetails())
                .hasSize(1)
                .contains(ErrorDetail.of("Year must be greater than 1894."));
    }

    @Test
    void save_nameIsNullAndYearLessThan1895() {
        var rq = new MediaSaveRequest();
        rq.setName(null);
        rq.setYear(randomYearLessThan1895());

        InvalidRqHttpResponse response = (InvalidRqHttpResponse) restService.save(rq);

        assertThat(response.status()).isEqualTo(HttpResponse.INVALID_RQ_STATUS);
        assertThat(response.errorDetails())
                .hasSize(2)
                .contains(ErrorDetail.of("Name must not be null or empty."))
                .contains(ErrorDetail.of("Year must be greater than 1894."));
    }

    @Test
    void save_success() {
        var rq = new MediaSaveRequest();
        rq.setName(randomString());
        rq.setYear(randomYearGreaterThan1895());
        when(delegate.save(rq))
                .thenReturn(new MediaSaveResponse(MediaDto.builder().setName(rq.getName()).setYear(rq.getYear()).build()));

        MediaSaveResponse response = (MediaSaveResponse) restService.save(rq);

        assertThat(response.status()).isEqualTo(HttpResponse.CREATED_STATUS);
        assertThat(response.media().getName()).isEqualTo(rq.getName());
        assertThat(response.media().getYear()).isEqualTo(rq.getYear());
    }

    private static int randomYearGreaterThan1895() {
        return ThreadLocalRandom.current().nextInt(1895, Integer.MAX_VALUE);
    }

    private static int randomYearLessThan1895() {
        return ThreadLocalRandom.current().nextInt(1, 1895);
    }
}
