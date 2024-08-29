package rgo.wm.media.tracker.persistence;

import rgo.wm.media.tracker.persistence.api.Genre;
import rgo.wm.media.tracker.persistence.api.GenreRepository;
import rgo.wm.spring.jdbc.TxWrapper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TxGenreRepositoryDecorator implements GenreRepository {

    private final TxWrapper txWrapper;
    private final GenreRepository delegate;

    public TxGenreRepositoryDecorator(TxWrapper txWrapper, GenreRepository delegate) {
        this.txWrapper = txWrapper;
        this.delegate = delegate;
    }

    @Nonnull
    @Override
    public List<Genre> findAll() {
        return txWrapper.tx(delegate::findAll);
    }

    @Override
    public Optional<Genre> findByUuid(UUID uuid) {
        return txWrapper.tx(() -> delegate.findByUuid(uuid));
    }
}
