package rgo.wm.media.tracker.service;

import rgo.wm.media.tracker.persistence.api.Media;
import rgo.wm.media.tracker.persistence.api.MediaRepository;
import rgo.wm.media.tracker.service.api.MediaDto;
import rgo.wm.media.tracker.service.api.MediaService;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MediaServiceImpl implements MediaService {

    private final MediaRepository repository;
    private final MediaMapper mapper;

    public MediaServiceImpl(MediaRepository repository, MediaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Nonnull
    @Override
    public List<MediaDto> findAll() {
        List<Media> media = repository.findAll();
        return media.stream().map(mapper::map).toList();
    }

    @Override
    public Optional<MediaDto> findByUuid(@Nonnull UUID uuid) {
        return repository.findByUuid(uuid).map(mapper::map);
    }

    @Nonnull
    @Override
    public MediaDto save(@Nonnull MediaDto media) {
        Media saved = repository.save(mapper.map(media));
        return mapper.map(saved);
    }

    public interface MediaMapper {

        Media map(MediaDto dto);

        MediaDto map(Media media);

        static MediaMapper defaultMapper() {
            return new MediaMapper() {

                @Override
                public Media map(MediaDto dto) {
                    return Media.builder()
                            .setUuid(dto.getUuid())
                            .setName(dto.getName())
                            .setYear(dto.getYear())
                            .build();
                }

                @Override
                public MediaDto map(Media media) {
                    return MediaDto.builder()
                            .setUuid(media.getUuid())
                            .setName(media.getName())
                            .setYear(media.getYear())
                            .build();
                }
            };
        }
    }
}
