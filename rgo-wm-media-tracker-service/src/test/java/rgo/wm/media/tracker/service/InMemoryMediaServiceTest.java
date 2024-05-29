package rgo.wm.media.tracker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rgo.wm.media.tracker.service.api.MediaDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.wm.common.test.utils.random.IntRandom.randomPositiveInt;
import static rgo.wm.common.test.utils.random.StringRandom.randomString;

class InMemoryMediaServiceTest {

    private InMemoryMediaService service;

    @BeforeEach
    void setUp() {
        service = new InMemoryMediaService();
    }

    @Test
    void findAll_empty() {
        List<MediaDto> actual = service.findAll();
        assertThat(actual).isEmpty();
    }

    @Test
    void findAll() {
        List<MediaDto> expected = insertRandoms();
        List<MediaDto> actual = service.findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void save() {
        MediaDto created = randomMediaDto();
        MediaDto saved = service.save(created);

        assertThat(saved.getName()).isEqualTo(created.getName());
        assertThat(saved.getYear()).isEqualTo(created.getYear());
    }

    private List<MediaDto> insertRandoms() {
        List<MediaDto> result = new ArrayList<>();

        IntStream.range(0, 10).forEach(i -> {
            MediaDto dto = randomMediaDto();
            MediaDto saved = service.save(dto);
            result.add(saved);
        });

        return result;
    }

    private MediaDto randomMediaDto() {
        return MediaDto.builder()
                .setName(randomString())
                .setYear(randomPositiveInt())
                .build();
    }
}