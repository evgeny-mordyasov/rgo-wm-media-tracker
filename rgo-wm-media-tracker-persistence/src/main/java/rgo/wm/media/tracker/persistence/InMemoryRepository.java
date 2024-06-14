package rgo.wm.media.tracker.persistence;

import rgo.wm.media.tracker.persistence.api.Media;
import rgo.wm.media.tracker.persistence.api.MediaRepository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository implements MediaRepository {

    private final Map<UUID, Media> storage;

    public InMemoryRepository() {
        this.storage = new ConcurrentHashMap<>();
    }

    @Nonnull
    @Override
    public List<Media> findAll() {
        return storage.values().stream().toList();
    }

    @Nonnull
    @Override
    public Media save(@Nonnull Media media) {
        Media assigned = assignUUID(media);
        storage.put(assigned.getUuid(), assigned);
        return assigned;
    }

    private Media assignUUID(Media media) {
        return Media.builder()
                .setUuid(UUID.randomUUID())
                .setName(media.getName())
                .setYear(media.getYear())
                .build();
    }
}
