package rgo.wm.media.tracker.service;

import rgo.wm.media.tracker.persistence.api.Genre;
import rgo.wm.media.tracker.persistence.api.GenreRepository;
import rgo.wm.media.tracker.service.api.GenreDto;
import rgo.wm.media.tracker.service.api.GenreService;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;
    private final GenreMapper mapper;

    public GenreServiceImpl(GenreRepository repository, GenreMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Nonnull
    @Override
    public List<GenreDto> findAll() {
        List<Genre> genres = repository.findAll();
        return convert(genres);
    }

    @Override
    public Optional<GenreDto> findByUuid(@Nonnull UUID uuid) {
        return repository.findByUuid(uuid).map(mapper::map);
    }

    private List<GenreDto> convert(List<Genre> genres) {
        return genres.stream().map(mapper::map).toList();
    }

    public interface GenreMapper {

        Genre map(GenreDto dto);

        GenreDto map(Genre genre);

        static GenreMapper defaultMapper() {

            return new GenreMapper() {
                @Override
                public Genre map(GenreDto dto) {
                    return Genre.builder()
                            .setUuid(dto.getUuid())
                            .setName(dto.getName())
                            .setDescription(dto.getDescription())
                            .build();
                }

                @Override
                public GenreDto map(Genre genre) {
                    return GenreDto.builder()
                            .setUuid(genre.getUuid())
                            .setName(genre.getName())
                            .setDescription(genre.getDescription())
                            .build();
                }
            };
        }
    }
}
