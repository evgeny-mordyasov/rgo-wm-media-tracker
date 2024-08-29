package rgo.wm.media.tracker.rest.api;

import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.media.tracker.rest.api.request.GenreGetByUuidRequest;

public interface GenreRestService {

    HttpResponse findAll();

    HttpResponse findByUuid(GenreGetByUuidRequest rq);
}
