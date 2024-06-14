package rgo.wm.media.tracker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rgo.wm.media.tracker.persistence.api.Media;
import rgo.wm.media.tracker.persistence.api.MediaRepository;
import rgo.wm.media.tracker.service.api.MediaDto;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rgo.wm.common.test.utils.random.IntRandom.randomPositiveInt;
import static rgo.wm.common.test.utils.random.StringRandom.randomString;

class MediaServiceImplTest {

    private MediaServiceImpl service;
    private MediaRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(MediaRepository.class);
        service = new MediaServiceImpl(repository);
    }

    @Test
    void findAll_empty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<MediaDto> result = service.findAll();
        assertThat(result).isNotNull().isEmpty();
    }

    @Test
    void findAll() {
        Media media = randomPersistentMedia();
        when(repository.findAll()).thenReturn(Collections.singletonList(media));

        List<MediaDto> result = service.findAll();

        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getUuid()).isEqualTo(media.getUuid());
        assertThat(result.get(0).getName()).isEqualTo(media.getName());
        assertThat(result.get(0).getYear()).isEqualTo(media.getYear());
    }

    @Test
    void save() {
        MediaDto created = randomMediaDto();
        Media stored = randomPersistentMedia(created);
        when(repository.save(any())).thenReturn(stored);

        MediaDto storedDto = service.save(created);

        assertThat(storedDto.getUuid()).isEqualTo(stored.getUuid());
        assertThat(storedDto.getName()).isEqualTo(stored.getName());
        assertThat(storedDto.getYear()).isEqualTo(stored.getYear());
    }

    private Media randomPersistentMedia() {
        return Media.builder()
                .setUuid(UUID.randomUUID())
                .setName(randomString())
                .setYear(randomPositiveInt())
                .build();
    }

    private Media randomPersistentMedia(MediaDto dto) {
        return Media.builder()
                .setUuid(UUID.randomUUID())
                .setName(dto.getName())
                .setYear(dto.getYear())
                .build();
    }

    private MediaDto randomMediaDto() {
        return MediaDto.builder()
                .setName(randomString())
                .setYear(randomPositiveInt())
                .build();
    }
}