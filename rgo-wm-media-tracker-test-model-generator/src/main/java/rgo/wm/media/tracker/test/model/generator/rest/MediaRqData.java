package rgo.wm.media.tracker.test.model.generator.rest;

import rgo.wm.media.tracker.rest.api.request.MediaGetByUuidRequest;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;

import static rgo.wm.common.test.utils.random.StringRandom.randomString;
import static rgo.wm.media.tracker.test.model.generator.Years.randomMediaYear;

public final class MediaRqData {

    private MediaRqData() {
    }

    public static MediaGetByUuidRequest randomGetByUuidRequest() {
        return MediaGetByUuidRequest.of(randomString());
    }

    public static MediaSaveRequest randomSaveRequest() {
        var rq = new MediaSaveRequest();
        rq.setName(randomString());
        rq.setYear(randomMediaYear());
        return rq;
    }
}
