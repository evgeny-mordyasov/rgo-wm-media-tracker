package rgo.wm.media.tracker.rest.api.response;

import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.rest.api.status.HttpStatus;
import rgo.wm.media.tracker.service.api.MediaDto;

public class MediaSaveResponse implements HttpResponse {

    private final MediaDto media;

    public MediaSaveResponse(MediaDto media) {
        this.media = media;
    }

    @Override
    public HttpStatus getStatus() {
        return CREATED_STATUS;
    }

    public MediaDto getMedia() {
        return media;
    }
}
