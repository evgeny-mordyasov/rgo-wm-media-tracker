package rgo.wm.media.tracker.persistence.api;

import javax.annotation.Nonnull;
import java.util.List;

public interface MediaRepository {

    @Nonnull
    List<Media> findAll();

    @Nonnull
    Media save(@Nonnull Media media);
}
