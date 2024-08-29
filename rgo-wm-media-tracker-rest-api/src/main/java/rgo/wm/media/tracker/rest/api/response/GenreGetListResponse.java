package rgo.wm.media.tracker.rest.api.response;

import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.rest.api.status.HttpStatus;
import rgo.wm.media.tracker.service.api.GenreDto;

import java.util.List;

public record GenreGetListResponse(List<GenreDto> genres) implements HttpResponse {

    @Override
    public HttpStatus status() {
        return SUCCESSFUL_STATUS;
    }
}
