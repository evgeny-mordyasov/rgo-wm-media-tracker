package rgo.wm.media.tracker.service.api;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MediaService {

    @Nonnull
    List<MediaDto> findAll();

    Optional<MediaDto> findByUuid(UUID uuid);

    @Nonnull
    MediaDto save(@Nonnull MediaDto media);
}
