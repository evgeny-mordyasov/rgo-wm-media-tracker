package rgo.wm.media.tracker.rest.api.response;

import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.rest.api.status.HttpStatus;
import rgo.wm.media.tracker.service.api.MediaDto;

import java.util.List;

public class MediaGetListResponse implements HttpResponse {

    private final List<MediaDto> media;

    public MediaGetListResponse(List<MediaDto> media) {
        this.media = media;
    }

    @Override
    public HttpStatus getStatus() {
        return SUCCESSFUL_STATUS;
    }

    public List<MediaDto> getMedia() {
        return media;
    }
}
