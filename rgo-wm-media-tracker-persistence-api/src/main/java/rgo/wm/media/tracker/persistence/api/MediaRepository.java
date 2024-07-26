package rgo.wm.media.tracker.persistence.api;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MediaRepository {

    @Nonnull
    List<Media> findAll();

    Optional<Media> findByUuid(UUID uuid);

    @Nonnull
    Media save(@Nonnull Media media);
}
