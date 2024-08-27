package rgo.wm.media.tracker.test.model.generator.service;

import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;
import rgo.wm.media.tracker.service.api.MediaDto;

import static java.util.UUID.randomUUID;
import static rgo.wm.common.test.utils.random.StringRandom.randomString;
import static rgo.wm.media.tracker.test.model.generator.Years.randomMediaYear;

public final class MediaDtoData {

    private MediaDtoData() {
    }

    public static MediaDto randomMediaDto() {
        return MediaDto.builder()
                .setName(randomString())
                .setYear(randomMediaYear())
                .build();
    }

    public static MediaDto randomPersistentMediaDto() {
        return MediaDto.builder()
                .setUuid(randomUUID())
                .setName(randomString())
                .setYear(randomMediaYear())
                .build();
    }

    public static MediaDto randomMediaDtoFrom(MediaSaveRequest rq) {
        return MediaDto.builder()
                .setUuid(randomUUID())
                .setName(rq.getName())
                .setYear(rq.getYear())
                .build();
    }
}
