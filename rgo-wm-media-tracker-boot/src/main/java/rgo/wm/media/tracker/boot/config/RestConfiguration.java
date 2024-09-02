package rgo.wm.media.tracker.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.validator.Validators;
import rgo.wm.common.utils.validator.rest.RestValidatorAdapter;
import rgo.wm.media.tracker.rest.InternalGenreRestService;
import rgo.wm.media.tracker.rest.InternalMediaRestService;
import rgo.wm.media.tracker.rest.ValidationGenreRestServiceDecorator;
import rgo.wm.media.tracker.rest.ValidationMediaRestServiceDecorator;
import rgo.wm.media.tracker.rest.api.GenreRestService;
import rgo.wm.media.tracker.rest.api.MediaRestService;
import rgo.wm.media.tracker.rest.api.request.GenreGetByUuidRequest;
import rgo.wm.media.tracker.rest.api.request.MediaGetByUuidRequest;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;
import rgo.wm.media.tracker.service.api.GenreService;
import rgo.wm.media.tracker.service.api.MediaService;
import rgo.wm.spring.web.InfoRequestLoggingFilter;

import static org.springframework.http.ResponseEntity.status;

@Configuration
public class RestConfiguration {

    @Bean
    public InfoRequestLoggingFilter logFilter() {
        InfoRequestLoggingFilter filter = new InfoRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(1000);
        filter.setIncludeHeaders(true);
        return filter;
    }

    @Bean
    public RestValidatorAdapter restValidatorAdapter() {
        return new RestValidatorAdapter(Validators.createValidator());
    }

    @Bean
    public GenreRestService genreRestService(GenreService genreService, RestValidatorAdapter validator) {
        return new ValidationGenreRestServiceDecorator(
                validator,
                new InternalGenreRestService(genreService));
    }

    @Bean
    public MediaRestService mediaRestService(MediaService mediaService, RestValidatorAdapter validator) {
        return new ValidationMediaRestServiceDecorator(
                validator,
                new InternalMediaRestService(mediaService));
    }

    @RestController
    @RequestMapping("/api/v1/genres")
    public static class GenreRestController {

        private final GenreRestService service;

        public GenreRestController(GenreRestService service) {
            this.service = service;
        }

        @GetMapping
        public ResponseEntity<HttpResponse> findAll() {
            HttpResponse response = service.findAll();
            return status(response.status().httpCode())
                    .body(response);
        }

        @GetMapping("/{uuid}")
        public ResponseEntity<HttpResponse> findByUuid(@PathVariable("uuid") String uuid) {
            HttpResponse response = service.findByUuid(GenreGetByUuidRequest.of(uuid));
            return status(response.status().httpCode())
                    .body(response);
        }
    }

    @RestController
    @RequestMapping("/api/v1/media")
    public static class MediaRestController {

        private final MediaRestService service;

        public MediaRestController(MediaRestService service) {
            this.service = service;
        }

        @GetMapping
        public ResponseEntity<HttpResponse> findAll() {
            HttpResponse response = service.findAll();
            return status(response.status().httpCode())
                    .body(response);
        }

        @GetMapping("/{uuid}")
        public ResponseEntity<HttpResponse> findByUuid(@PathVariable("uuid") String uuid) {
            HttpResponse response = service.findByUuid(MediaGetByUuidRequest.of(uuid));
            return status(response.status().httpCode())
                    .body(response);
        }

        @PostMapping
        public ResponseEntity<HttpResponse> save(@RequestBody MediaSaveRequest rq) {
            HttpResponse response = service.save(rq);
            return status(response.status().httpCode())
                    .body(response);
        }
    }
}
