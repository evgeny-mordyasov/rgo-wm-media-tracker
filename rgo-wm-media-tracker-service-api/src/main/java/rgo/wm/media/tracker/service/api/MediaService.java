package rgo.wm.media.tracker.service.api;

import javax.annotation.Nonnull;
import java.util.List;

public interface MediaService {

    @Nonnull
    List<MediaDto> findAll();

    @Nonnull
    MediaDto save(@Nonnull MediaDto media);
}
