package rgo.wm.media.tracker.rest.api;

import rgo.wm.common.utils.rest.api.ErrorDetail;
import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.validator.ValidatorAdapter;
import rgo.wm.media.tracker.rest.api.request.MediaGetByUuidRequest;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;

import java.util.List;

public interface MediaRestService {

    HttpResponse findAll();

    HttpResponse findByUuid(MediaGetByUuidRequest rq);

    HttpResponse save(MediaSaveRequest rq);

    default MediaRestService withValidation(ValidatorAdapter validator) {
        var delegate = this;

        return new MediaRestService() {

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
        };
    }
}
