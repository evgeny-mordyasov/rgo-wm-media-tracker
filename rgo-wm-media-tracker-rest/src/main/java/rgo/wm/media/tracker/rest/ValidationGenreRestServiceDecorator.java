package rgo.wm.media.tracker.rest;

import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.validator.rest.RestValidatorAdapter;
import rgo.wm.media.tracker.rest.api.GenreRestService;
import rgo.wm.media.tracker.rest.api.request.GenreGetByUuidRequest;

public class ValidationGenreRestServiceDecorator implements GenreRestService {

    private final RestValidatorAdapter validator;
    private final GenreRestService delegate;

    public ValidationGenreRestServiceDecorator(RestValidatorAdapter validator, GenreRestService delegate) {
        this.validator = validator;
        this.delegate = delegate;
    }

    @Override
    public HttpResponse findAll() {
        return delegate.findAll();
    }

    @Override
    public HttpResponse findByUuid(GenreGetByUuidRequest rq) {
        return validator.validate(rq, () -> delegate.findByUuid(rq));
    }
}
