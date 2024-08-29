package rgo.wm.media.tracker.test.model.generator.rest;

import rgo.wm.common.test.utils.random.StringRandom;
import rgo.wm.media.tracker.rest.api.request.GenreGetByUuidRequest;

import java.util.List;
import java.util.stream.Stream;

import static rgo.wm.common.test.utils.random.StringRandom.randomString;

public final class GenreRqData {

    private GenreRqData() {
    }

    public static GenreGetByUuidRequest randomGetByUuidRequest() {
        return GenreGetByUuidRequest.of(randomString());
    }

    public static List<String> randomGenres() {
        return randomGenres(10);
    }

    public static List<String> randomGenres(int n) {
        return Stream.generate(StringRandom::randomUUIDAsString)
                .limit(n)
                .toList();
    }
}
