package rgo.wm.media.tracker.service.api;

import org.junit.jupiter.api.Test;
import rgo.wm.common.utils.asserts.AssertsException;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static rgo.wm.common.test.utils.random.IntRandom.randomPositiveInt;
import static rgo.wm.common.test.utils.random.StringRandom.randomString;

class MediaDtoTest {

    @Test
    void successBuild() {
        assertThatNoException()
                .isThrownBy(randomDefaultBuilder()::build);
    }

    @Test
    void gettersReturnNonNull() {
        var media = randomDefaultBuilder().build();

        assertThat(media.getName()).isNotNull();
    }

    @Test
    void nameIsNull() {
        var builder = randomDefaultBuilder().setName(null);
        assertThatThrownBy(builder::build)
                .isInstanceOf(AssertsException.class)
                .hasMessage("'name' must not be null");
    }

    @Test
    void yearIsNegative() {
        var builder = randomDefaultBuilder().setYear(-randomPositiveInt());
        assertThatThrownBy(builder::build)
                .isInstanceOf(AssertsException.class)
                .hasMessage("'year' must not be negative");
    }

    private MediaDto.Builder randomDefaultBuilder() {
        return MediaDto.builder()
                .setUuid(randomUUID())
                .setName(randomString())
                .setYear(randomPositiveInt());
    }
}
