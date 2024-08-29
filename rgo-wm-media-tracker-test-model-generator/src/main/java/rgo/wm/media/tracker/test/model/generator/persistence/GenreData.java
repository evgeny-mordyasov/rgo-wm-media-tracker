package rgo.wm.media.tracker.test.model.generator.persistence;

import rgo.wm.media.tracker.persistence.api.Genre;
import rgo.wm.media.tracker.service.api.GenreDto;

import java.util.List;
import java.util.stream.Stream;

import static java.util.UUID.randomUUID;
import static rgo.wm.common.test.utils.random.StringRandom.randomString;

public final class GenreData {

    private GenreData() {
    }

    public static Genre randomGenre() {
        return Genre.builder()
                .setName(randomString())
                .setDescription(randomString())
                .build();
    }

    public static Genre randomPersistentGenre() {
        return Genre.builder()
                .setUuid(randomUUID())
                .setName(randomString())
                .setDescription(randomString())
                .build();
    }

    public static Genre randomPersistentGenreFrom(GenreDto dto) {
        return Genre.builder()
                .setUuid(randomUUID())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .build();
    }

    public static List<Genre> randomPersistentGenreFrom(List<GenreDto> dto) {
        return dto.stream().map(GenreData::randomPersistentGenreFrom).toList();
    }

    public static List<Genre> randomPersistentGenres() {
        return randomPersistentGenres(10);
    }

    public static List<Genre> randomPersistentGenres(int n) {
        return Stream.generate(GenreData::randomPersistentGenre)
                .limit(n)
                .toList();
    }
}
