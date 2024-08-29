package rgo.wm.media.tracker.persistence.api;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreRepository {

    @Nonnull
    List<Genre> findAll();

    Optional<Genre> findByUuid(UUID uuid);
}
