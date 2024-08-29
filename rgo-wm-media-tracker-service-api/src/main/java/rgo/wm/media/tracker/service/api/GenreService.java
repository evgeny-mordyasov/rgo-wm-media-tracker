package rgo.wm.media.tracker.service.api;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreService {

    @Nonnull
    List<GenreDto> findAll();

    Optional<GenreDto> findByUuid(@Nonnull UUID uuid);
}
