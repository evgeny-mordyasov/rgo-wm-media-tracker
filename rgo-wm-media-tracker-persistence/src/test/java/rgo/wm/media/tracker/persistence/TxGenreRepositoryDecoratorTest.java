package rgo.wm.media.tracker.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rgo.wm.media.tracker.persistence.api.GenreRepository;
import rgo.wm.spring.jdbc.TxWrapper;

import java.util.UUID;
import java.util.function.Supplier;

import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
class TxGenreRepositoryDecoratorTest {

    private TxGenreRepositoryDecorator decorator;
    private TxWrapper txWrapper;
    private GenreRepository delegate;

    @BeforeEach
    void setUp() {
        txWrapper = mock(TxWrapper.class);
        delegate = mock(GenreRepository.class);
        decorator = new TxGenreRepositoryDecorator(txWrapper, delegate);
    }

    @Test
    void findAll() {
        doAnswer(invocation -> {
            delegate.findAll();
            return null;
        }).when(txWrapper).tx(any(Supplier.class));

        decorator.findAll();

        verify(delegate).findAll();
        verify(txWrapper).tx(any(Supplier.class));
    }

    @Test
    void findByUuid() {
        UUID uuid = randomUUID();
        doAnswer(invocation -> {
            delegate.findByUuid(uuid);
            return null;
        }).when(txWrapper).tx(any(Supplier.class));

        decorator.findByUuid(any());

        verify(delegate).findByUuid(uuid);
        verify(txWrapper).tx(any(Supplier.class));
    }
}