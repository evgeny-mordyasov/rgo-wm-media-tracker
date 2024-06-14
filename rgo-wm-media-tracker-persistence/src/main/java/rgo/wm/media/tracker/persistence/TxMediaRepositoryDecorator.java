package rgo.wm.media.tracker.persistence;

import rgo.wm.media.tracker.persistence.api.Media;
import rgo.wm.media.tracker.persistence.api.MediaRepository;
import rgo.wm.spring.jdbc.TxWrapper;

import javax.annotation.Nonnull;
import java.util.List;

public class TxMediaRepositoryDecorator implements MediaRepository  {

    private final TxWrapper txWrapper;
    private final MediaRepository delegate;

    public TxMediaRepositoryDecorator(TxWrapper txWrapper, MediaRepository delegate) {
        this.txWrapper = txWrapper;
        this.delegate = delegate;
    }

    @Nonnull
    @Override
    public List<Media> findAll() {
        return txWrapper.tx(delegate::findAll);
    }

    @Nonnull
    @Override
    public Media save(@Nonnull Media media) {
        return txWrapper.tx(() -> delegate.save(media));
    }
}
