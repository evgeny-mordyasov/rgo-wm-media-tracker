package rgo.wm.media.tracker.rest.api;

import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.media.tracker.rest.api.request.MediaGetByUuidRequest;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;
import rgo.wm.media.tracker.rest.api.response.MediaGetByUuidResponse;
import rgo.wm.media.tracker.rest.api.response.MediaGetListResponse;
import rgo.wm.media.tracker.rest.api.response.MediaSaveResponse;
import rgo.wm.media.tracker.service.api.MediaDto;
import rgo.wm.media.tracker.service.api.MediaService;

import java.util.List;
import java.util.Optional;

public class MediaRestServiceImpl implements MediaRestService {

    private final MediaService service;

    public MediaRestServiceImpl(MediaService service) {
        this.service = service;
    }

    @Override
    public HttpResponse findAll() {
        List<MediaDto> media = service.findAll();
        return new MediaGetListResponse(media);
    }

    @Override
    public HttpResponse findByUuid(MediaGetByUuidRequest rq) {
        Optional<MediaDto> opt = service.findByUuid(rq.getUuid());
        return opt.isPresent()
                ? new MediaGetByUuidResponse(opt.get())
                : HttpResponse.notFound();
    }

    @Override
    public HttpResponse save(MediaSaveRequest rq) {
        MediaDto created = createMedia(rq);
        MediaDto saved = service.save(created);
        return new MediaSaveResponse(saved);
    }

    private MediaDto createMedia(MediaSaveRequest rq) {
        return MediaDto.builder()
                .setName(rq.getName())
                .setYear(rq.getYear())
                .build();
    }
}
