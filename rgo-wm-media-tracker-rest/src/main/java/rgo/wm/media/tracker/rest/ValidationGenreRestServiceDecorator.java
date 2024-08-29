package rgo.wm.media.tracker.rest;

import rgo.wm.common.utils.rest.api.ErrorDetail;
import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.validator.ValidatorAdapter;
import rgo.wm.media.tracker.rest.api.GenreRestService;
import rgo.wm.media.tracker.rest.api.request.GenreGetByUuidRequest;

import java.util.List;

public class ValidationGenreRestServiceDecorator implements GenreRestService {

    private final ValidatorAdapter validator;
    private final GenreRestService delegate;

    public ValidationGenreRestServiceDecorator(ValidatorAdapter validator, GenreRestService delegate) {
        this.validator = validator;
        this.delegate = delegate;
    }

    @Override
    public HttpResponse findAll() {
        return delegate.findAll();
    }

    @Override
    public HttpResponse findByUuid(GenreGetByUuidRequest rq) {
        List<String> errorMessages = validator.validate(rq);
        if (errorMessages.isEmpty()) {
            return delegate.findByUuid(rq);
        }

        return HttpResponse.invalidRq(ErrorDetail.of(errorMessages));
    }
}
