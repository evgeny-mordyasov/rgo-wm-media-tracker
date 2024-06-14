package rgo.wm.media.tracker.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rgo.wm.media.tracker.persistence.api.Media;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.wm.common.test.utils.random.IntRandom.randomPositiveInt;
import static rgo.wm.common.test.utils.random.StringRandom.randomString;

class InMemoryRepositoryTest {

    private InMemoryRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRepository();
    }

    @Test
    void findAll_empty() {
        List<Media> actual = repository.findAll();
        assertThat(actual).isEmpty();
    }

    @Test
    void findAll() {
        List<Media> expected = insertRandoms();
        List<Media> actual = repository.findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void save() {
        Media created = randomMedia();
        Media saved = repository.save(created);

        assertThat(saved.getName()).isEqualTo(created.getName());
        assertThat(saved.getYear()).isEqualTo(created.getYear());
    }

    private List<Media> insertRandoms() {
        List<Media> result = new ArrayList<>();

        IntStream.range(0, 10).forEach(i -> {
            Media dto = randomMedia();
            Media saved = repository.save(dto);
            result.add(saved);
        });

        return result;
    }

    private Media randomMedia() {
        return Media.builder()
                .setName(randomString())
                .setYear(randomPositiveInt())
                .build();
    }
}