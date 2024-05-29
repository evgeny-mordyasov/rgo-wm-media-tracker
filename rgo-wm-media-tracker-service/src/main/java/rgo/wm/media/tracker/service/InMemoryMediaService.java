package rgo.wm.media.tracker.service;

import rgo.wm.media.tracker.service.api.MediaDto;
import rgo.wm.media.tracker.service.api.MediaService;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMediaService implements MediaService {

    private final Map<UUID, MediaDto> storage;

    public InMemoryMediaService() {
        this.storage = new ConcurrentHashMap<>();
    }

    @Nonnull
    @Override
    public List<MediaDto> findAll() {
        return storage.values().stream().toList();
    }

    @Nonnull
    @Override
    public MediaDto save(@Nonnull MediaDto media) {
        MediaDto assigned = assignUUID(media);
        storage.put(assigned.getUuid(), assigned);
        return assigned;
    }

    private MediaDto assignUUID(MediaDto media) {
        return MediaDto.builder()
                .setUuid(UUID.randomUUID())
                .setName(media.getName())
                .setYear(media.getYear())
                .build();
    }
}
