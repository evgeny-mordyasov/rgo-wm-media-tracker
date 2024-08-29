package rgo.wm.media.tracker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rgo.wm.media.tracker.persistence.api.Genre;
import rgo.wm.media.tracker.persistence.api.GenreRepository;
import rgo.wm.media.tracker.service.GenreServiceImpl.GenreMapper;
import rgo.wm.media.tracker.service.api.GenreDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rgo.wm.media.tracker.test.model.generator.persistence.GenreData.randomPersistentGenre;

class GenreServiceImplTest {

    private GenreServiceImpl service;
    private GenreRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(GenreRepository.class);
        var mapper = GenreMapper.defaultMapper();
        service = new GenreServiceImpl(repository, mapper);
    }

    @Test
    void findAll_empty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<GenreDto> result = service.findAll();
        assertThat(result).isNotNull().isEmpty();
    }

    @Test
    void findAll() {
        Genre genre = randomPersistentGenre();
        when(repository.findAll()).thenReturn(Collections.singletonList(genre));

        List<GenreDto> result = service.findAll();

        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getUuid()).isEqualTo(genre.getUuid());
        assertThat(result.get(0).getName()).isEqualTo(genre.getName());
        assertThat(result.get(0).getDescription()).isEqualTo(genre.getDescription());
    }

    @Test
    void findByUuid_empty() {
        UUID uuid = randomUUID();
        when(repository.findByUuid(uuid)).thenReturn(Optional.empty());

        Optional<GenreDto> opt = service.findByUuid(uuid);

        assertThat(opt).isEmpty();
    }

    @Test
    void findByUuid() {
        Genre genre = randomPersistentGenre();
        assert genre.getUuid() != null;
        when(repository.findByUuid(genre.getUuid())).thenReturn(Optional.of(genre));

        Optional<GenreDto> opt = service.findByUuid(genre.getUuid());

        assertThat(opt).isPresent();
        assertThat(opt.get().getUuid()).isEqualTo(genre.getUuid());
        assertThat(opt.get().getName()).isEqualTo(genre.getName());
        assertThat(opt.get().getDescription()).isEqualTo(genre.getDescription());
    }
}