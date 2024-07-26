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

    public MediaServiceImpl(MediaRepository repository) {
        this.repository = repository;
    }

    @Nonnull
    @Override
    public List<MediaDto> findAll() {
        List<Media> media = repository.findAll();
        return convert(media);
    }

    @Override
    public Optional<MediaDto> findByUuid(UUID uuid) {
        return repository.findByUuid(uuid).map(this::convert);
    }

    private List<MediaDto> convert(List<Media> media) {
        return media.stream().map(this::convert).toList();
    }

    private MediaDto convert(Media media) {
        return MediaDto.builder()
                .setUuid(media.getUuid())
                .setName(media.getName())
                .setYear(media.getYear())
                .build();
    }

    @Nonnull
    @Override
    public MediaDto save(@Nonnull MediaDto media) {
        Media saved = repository.save(convert(media));
        return convert(saved);
    }

    private Media convert(MediaDto dto) {
        return Media.builder()
                .setUuid(dto.getUuid())
                .setName(dto.getName())
                .setYear(dto.getYear())
                .build();
    }
}
