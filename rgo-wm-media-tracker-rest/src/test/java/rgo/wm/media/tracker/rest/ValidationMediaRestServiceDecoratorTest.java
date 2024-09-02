package rgo.wm.media.tracker.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rgo.wm.common.utils.rest.api.ErrorDetail;
import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.rest.api.InvalidRqHttpResponse;
import rgo.wm.common.utils.validator.rest.RestValidatorAdapter;
import rgo.wm.media.tracker.rest.api.MediaRestService;
import rgo.wm.media.tracker.rest.api.request.MediaGetByUuidRequest;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;
import rgo.wm.media.tracker.rest.api.response.MediaSaveResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static rgo.wm.common.utils.validator.Validators.createValidator;
import static rgo.wm.media.tracker.test.model.generator.Years.randomMediaYearLessThan1895;
import static rgo.wm.media.tracker.test.model.generator.rest.MediaRqData.randomSaveRequest;
import static rgo.wm.media.tracker.test.model.generator.service.MediaDtoData.randomMediaDtoFrom;

class ValidationMediaRestServiceDecoratorTest {

    private static final RestValidatorAdapter VALIDATOR = new RestValidatorAdapter(createValidator());

    private MediaRestService restService;
    private MediaRestService delegate;

    @BeforeEach
    void setUp() {
        delegate = spy(MediaRestService.class);
        restService = new ValidationMediaRestServiceDecorator(VALIDATOR, delegate);
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
        var rq = randomSaveRequest();
        rq.setName(null);

        InvalidRqHttpResponse response = (InvalidRqHttpResponse) restService.save(rq);

        assertThat(response.status()).isEqualTo(HttpResponse.INVALID_RQ_STATUS);
        assertThat(response.errorDetails())
                .hasSize(1)
                .contains(ErrorDetail.of("Name must not be null or empty."));
    }

    @Test
    void save_yearLessThan1895() {
        var rq = randomSaveRequest();
        rq.setYear(randomMediaYearLessThan1895());

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
        rq.setYear(randomMediaYearLessThan1895());

        InvalidRqHttpResponse response = (InvalidRqHttpResponse) restService.save(rq);

        assertThat(response.status()).isEqualTo(HttpResponse.INVALID_RQ_STATUS);
        assertThat(response.errorDetails())
                .hasSize(2)
                .contains(ErrorDetail.of("Name must not be null or empty."))
                .contains(ErrorDetail.of("Year must be greater than 1894."));
    }

    @Test
    void save_success() {
        var rq = randomSaveRequest();
        when(delegate.save(rq)).thenReturn(new MediaSaveResponse(randomMediaDtoFrom(rq)));

        MediaSaveResponse response = (MediaSaveResponse) restService.save(rq);

        assertThat(response.status()).isEqualTo(HttpResponse.CREATED_STATUS);
        assertThat(response.media().getName()).isEqualTo(rq.getName());
        assertThat(response.media().getYear()).isEqualTo(rq.getYear());
    }
}
