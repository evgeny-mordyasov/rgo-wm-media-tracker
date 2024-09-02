package rgo.wm.media.tracker.rest;

import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.validator.rest.RestValidatorAdapter;
import rgo.wm.media.tracker.rest.api.MediaRestService;
import rgo.wm.media.tracker.rest.api.request.MediaGetByUuidRequest;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;

public class ValidationMediaRestServiceDecorator implements MediaRestService {

    private final RestValidatorAdapter validator;
    private final MediaRestService delegate;

    public ValidationMediaRestServiceDecorator(RestValidatorAdapter validator, MediaRestService delegate) {
        this.validator = validator;
        this.delegate = delegate;
    }

    @Override
    public HttpResponse findAll() {
        return delegate.findAll();
    }

    @Override
    public HttpResponse findByUuid(MediaGetByUuidRequest rq) {
        return validator.validate(rq, () -> delegate.findByUuid(rq));
    }

    @Override
    public HttpResponse save(MediaSaveRequest rq) {
        return validator.validate(rq, () -> delegate.save(rq));
    }
}
