package rgo.wm.media.tracker.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rgo.wm.common.utils.rest.api.ErrorDetail;
import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.rest.api.InvalidRqHttpResponse;
import rgo.wm.common.utils.validator.ValidatorAdapter;
import rgo.wm.media.tracker.rest.api.GenreRestService;
import rgo.wm.media.tracker.rest.api.request.GenreGetByUuidRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static rgo.wm.common.utils.validator.Validators.createValidator;

class ValidationGenreRestServiceDecoratorTest {

    private static final ValidatorAdapter VALIDATOR = createValidator();

    private GenreRestService restService;

    @BeforeEach
    void setUp() {
        var delegate = spy(GenreRestService.class);
        restService = new ValidationGenreRestServiceDecorator(VALIDATOR, delegate);
    }

    @Test
    void findByUuid_uuidIsInvalid() {
        var rq = new GenreGetByUuidRequest();
        rq.setUuid("abc");

        InvalidRqHttpResponse response = (InvalidRqHttpResponse) restService.findByUuid(rq);

        assertThat(response.status()).isEqualTo(HttpResponse.INVALID_RQ_STATUS);
        assertThat(response.errorDetails())
                .hasSize(1)
                .contains(ErrorDetail.of("UUID not a valid."));
    }
}
