package rgo.wm.media.tracker.rest;

import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.media.tracker.rest.api.GenreRestService;
import rgo.wm.media.tracker.rest.api.request.GenreGetByUuidRequest;
import rgo.wm.media.tracker.rest.api.response.GenreGetByUuidResponse;
import rgo.wm.media.tracker.rest.api.response.GenreGetListResponse;
import rgo.wm.media.tracker.service.api.GenreDto;
import rgo.wm.media.tracker.service.api.GenreService;

import java.util.List;
import java.util.Optional;

public class InternalGenreRestService implements GenreRestService {

    private final GenreService service;

    public InternalGenreRestService(GenreService service) {
        this.service = service;
    }

    @Override
    public HttpResponse findAll() {
        List<GenreDto> genres = service.findAll();
        return new GenreGetListResponse(genres);
    }

    @Override
    public HttpResponse findByUuid(GenreGetByUuidRequest rq) {
        Optional<GenreDto> opt = service.findByUuid(rq.getUuid());
        return opt.isPresent()
                ? new GenreGetByUuidResponse(opt.get())
                : HttpResponse.notFound();
    }
}
