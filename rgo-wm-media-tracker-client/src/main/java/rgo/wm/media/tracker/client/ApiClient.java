package rgo.wm.media.tracker.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.net.URI;

public class ApiClient {

    private final RestClient restClient;

    public ApiClient(String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(URI.create(baseUrl))
                .build();
    }

    public <T> ResponseEntity<T> get(String uri, Class<T> responseType) {
        return restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(responseType);
    }

    public <T> ResponseEntity<T> getList(String uri) {
        return restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>(){});
    }

    public ResponseEntity<String> post(String uri, Object request) throws RestClientException {
        return restClient.post()
                .uri(uri)
                .body(request)
                .retrieve()
                .toEntity(String.class);
    }
}
