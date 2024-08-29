package rgo.wm.media.tracker.test.model.generator.service;

import rgo.wm.media.tracker.service.api.GenreDto;

import java.util.List;
import java.util.stream.Stream;

import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;
import static rgo.wm.common.test.utils.random.StringRandom.randomString;

public final class GenreDtoData {

    private GenreDtoData() {
    }

    public static GenreDto randomPersistentGenreDto() {
        return GenreDto.builder()
                .setUuid(randomUUID())
                .setName(randomString())
                .setDescription(randomString())
                .build();
    }

    public static GenreDto randomPersistentGenreDtoFrom(String uuid) {
        return GenreDto.builder()
                .setUuid(fromString(uuid))
                .setName(randomString())
                .setDescription(randomString())
                .build();
    }

    public static List<GenreDto> randomPersistentGenreDtoFrom(List<String> genres) {
        return genres.stream().map(GenreDtoData::randomPersistentGenreDtoFrom).toList();
    }

    public static List<GenreDto> randomPersistentGenresDto() {
        return randomPersistentGenresDto(10);
    }

    public static List<GenreDto> randomPersistentGenresDto(int n) {
        return Stream.generate(GenreDtoData::randomPersistentGenreDto)
                .limit(n)
                .toList();
    }
}
