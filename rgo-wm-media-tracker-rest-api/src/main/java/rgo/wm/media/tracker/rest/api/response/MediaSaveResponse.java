package rgo.wm.media.tracker.rest.api.response;

import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.rest.api.status.HttpStatus;
import rgo.wm.media.tracker.service.api.MediaDto;

public record MediaSaveResponse(MediaDto media) implements HttpResponse {

    @Override
    public HttpStatus status() {
        return CREATED_STATUS;
    }
}
