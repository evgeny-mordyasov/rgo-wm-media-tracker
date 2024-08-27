package rgo.wm.media.tracker.rest;

import rgo.wm.common.utils.rest.api.ErrorDetail;
import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.validator.ValidatorAdapter;
import rgo.wm.media.tracker.rest.api.MediaRestService;
import rgo.wm.media.tracker.rest.api.request.MediaGetByUuidRequest;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;

import java.util.List;

public class ValidationMediaRestServiceDecorator implements MediaRestService {

    private final ValidatorAdapter validator;
    private final MediaRestService delegate;

    public ValidationMediaRestServiceDecorator(ValidatorAdapter validator, MediaRestService delegate) {
        this.validator = validator;
        this.delegate = delegate;
    }

    @Override
    public HttpResponse findAll() {
        return delegate.findAll();
    }

    @Override
    public HttpResponse findByUuid(MediaGetByUuidRequest rq) {
        List<String> errorMessages = validator.validate(rq);
        if (errorMessages.isEmpty()) {
            return delegate.findByUuid(rq);
        }

        return HttpResponse.invalidRq(ErrorDetail.of(errorMessages));
    }

    @Override
    public HttpResponse save(MediaSaveRequest rq) {
        List<String> errorMessages = validator.validate(rq);
        if (errorMessages.isEmpty()) {
            return delegate.save(rq);
        }

        return HttpResponse.invalidRq(ErrorDetail.of(errorMessages));
    }
}
