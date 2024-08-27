package rgo.wm.media.tracker.test.model.generator.persistence;

import rgo.wm.media.tracker.persistence.api.Media;
import rgo.wm.media.tracker.service.api.MediaDto;

import static java.util.UUID.randomUUID;
import static rgo.wm.common.test.utils.random.StringRandom.randomString;
import static rgo.wm.media.tracker.test.model.generator.Years.randomMediaYear;

public final class MediaData {

    private MediaData() {
    }

    public static Media randomMedia() {
        return Media.builder()
                .setName(randomString())
                .setYear(randomMediaYear())
                .build();
    }

    public static Media randomPersistentMedia() {
        return Media.builder()
                .setUuid(randomUUID())
                .setName(randomString())
                .setYear(randomMediaYear())
                .build();
    }

    public static Media randomPersistentMediaFrom(MediaDto dto) {
        return Media.builder()
                .setUuid(randomUUID())
                .setName(dto.getName())
                .setYear(dto.getYear())
                .build();
    }
}
