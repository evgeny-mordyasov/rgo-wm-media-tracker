package rgo.wm.media.tracker.service.api;

import org.junit.jupiter.api.Test;
import rgo.wm.common.utils.asserts.AssertsException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static rgo.wm.common.test.utils.random.IntRandom.randomPositiveInt;
import static rgo.wm.common.test.utils.random.StringRandom.randomString;

class MediaDtoTest {

    @Test
    void successBuild() {
        MediaDto.Builder builder = MediaDto.builder()
                .setUuid(UUID.randomUUID())
                .setName(randomString())
                .setYear(randomPositiveInt());

        assertThatNoException().isThrownBy(builder::build);
    }

    @Test
    void gettersReturnNonNull() {
        MediaDto media = MediaDto.builder()
                .setUuid(UUID.randomUUID())
                .setName(randomString())
                .setYear(randomPositiveInt())
                .build();

        assertThat(media.getName()).isNotNull();
    }

    @Test
    void nameIsNull() {
        MediaDto.Builder builder = MediaDto.builder()
                .setUuid(UUID.randomUUID())
                .setName(null)
                .setYear(randomPositiveInt());

        assertThatThrownBy(builder::build)
                .isInstanceOf(AssertsException.class)
                .hasMessage("'name' must not be null");
    }

    @Test
    void yearIsNegative() {
        MediaDto.Builder builder = MediaDto.builder()
                .setUuid(UUID.randomUUID())
                .setName(randomString())
                .setYear(-randomPositiveInt());

        assertThatThrownBy(builder::build)
                .isInstanceOf(AssertsException.class)
                .hasMessage("'year' must not be negative");
    }
}
