package rgo.wm.media.tracker.client;

import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.media.tracker.rest.api.MediaRestService;
import rgo.wm.media.tracker.rest.api.request.MediaGetByUuidRequest;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;
import rgo.wm.media.tracker.service.api.GenreDto;

import java.util.List;

public class GenreClient {

    private final ApiClient apiClient;
    private static final String BASE_PATH = "/api/v1/genres";

    public GenreClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<GenreDto> findAll() {
        return apiClient.getList(BASE_PATH);
    }

    public HttpResponse findByUuid(MediaGetByUuidRequest rq) {
        return null;
    }

    public HttpResponse save(MediaSaveRequest rq) {
        return null;
    }
}
