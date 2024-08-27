package rgo.wm.media.tracker.rest.api;

import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.media.tracker.rest.api.request.MediaGetByUuidRequest;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;

public interface MediaRestService {

    HttpResponse findAll();

    HttpResponse findByUuid(MediaGetByUuidRequest rq);

    HttpResponse save(MediaSaveRequest rq);
}
