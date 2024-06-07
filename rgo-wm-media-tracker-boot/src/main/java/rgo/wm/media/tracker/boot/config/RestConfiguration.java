package rgo.wm.media.tracker.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rgo.wm.common.utils.rest.api.HttpResponse;
import rgo.wm.common.utils.validator.ValidatorAdapter;
import rgo.wm.media.tracker.rest.api.MediaRestService;
import rgo.wm.media.tracker.rest.api.MediaRestServiceImpl;
import rgo.wm.media.tracker.rest.api.request.MediaSaveRequest;
import rgo.wm.media.tracker.service.api.MediaService;

@Configuration
public class RestConfiguration {

    @Bean
    public MediaRestService restService(MediaService service, ValidatorAdapter validator) {
        return new MediaRestServiceImpl(service).withValidation(validator);
    }

    @RestController
    @RequestMapping("/api/v1/media")
    public static class MediaRestController {

        private final MediaRestService service;

        public MediaRestController(MediaRestService service) {
            this.service = service;
        }

        @GetMapping
        public HttpResponse findAll() {
            return service.findAll();
        }

        @PostMapping
        public HttpResponse save(@RequestBody MediaSaveRequest rq) {
            return service.save(rq);
        }
    }
}
