package rgo.wm.media.tracker.rest.api.response;

import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.rest.api.status.HttpStatus;
import rgo.wm.media.tracker.service.api.GenreDto;

public record GenreGetByUuidResponse(GenreDto genre) implements HttpResponse {

    @Override
    public HttpStatus status() {
        return SUCCESSFUL_STATUS;
    }
}
