package rgo.wm.media.tracker.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rgo.wm.common.utils.validator.ValidatorAdapter;
import rgo.wm.common.utils.validator.Validators;
import rgo.wm.media.tracker.persistence.api.GenreRepository;
import rgo.wm.media.tracker.persistence.api.MediaRepository;
import rgo.wm.media.tracker.service.GenreServiceImpl;
import rgo.wm.media.tracker.service.GenreServiceImpl.GenreMapper;
import rgo.wm.media.tracker.service.MediaServiceImpl;
import rgo.wm.media.tracker.service.api.GenreService;
import rgo.wm.media.tracker.service.api.MediaService;

@Configuration
public class ServiceConfiguration {

    @Bean
    public ValidatorAdapter validatorAdapter() {
        return Validators.createValidator();
    }

    @Bean
    public GenreMapper genreMapper() {
        return GenreMapper.defaultMapper();
    }

    @Bean
    public GenreService genreService(GenreRepository repository, GenreMapper mapper) {
        return new GenreServiceImpl(repository, mapper);
    }

    @Bean
    public MediaService mediaService(MediaRepository repository) {
        return new MediaServiceImpl(repository);
    }
}
