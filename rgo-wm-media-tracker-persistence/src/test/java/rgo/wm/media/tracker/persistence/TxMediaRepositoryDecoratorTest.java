package rgo.wm.media.tracker.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rgo.wm.media.tracker.persistence.api.Media;
import rgo.wm.media.tracker.persistence.api.MediaRepository;
import rgo.wm.spring.jdbc.TxWrapper;

import java.util.UUID;
import java.util.function.Supplier;

import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static rgo.wm.media.tracker.test.model.generator.persistence.MediaData.randomMedia;

@SuppressWarnings("unchecked")
class TxMediaRepositoryDecoratorTest {

    private TxMediaRepositoryDecorator decorator;
    private TxWrapper txWrapper;
    private MediaRepository delegate;

    @BeforeEach
    void setUp() {
        txWrapper = mock(TxWrapper.class);
        delegate = mock(MediaRepository.class);
        decorator = new TxMediaRepositoryDecorator(txWrapper, delegate);
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

        decorator.save(any());

        verify(delegate).findByUuid(uuid);
        verify(txWrapper).tx(any(Supplier.class));
    }

    @Test
    void save() {
        Media media = randomMedia();
        doAnswer(invocation -> {
            delegate.save(media);
            return null;
        }).when(txWrapper).tx(any(Supplier.class));

        decorator.save(any());

        verify(delegate).save(media);
        verify(txWrapper).tx(any(Supplier.class));
    }
}