package rgo.wm.media.tracker.service.api;

import rgo.wm.common.utils.asserts.Asserts;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

import static java.util.UUID.fromString;

public class GenreDto {

    private final UUID uuid;
    private final String name;
    private final String description;

    private GenreDto(Builder builder) {
        this.uuid = builder.uuid;
        this.name = Asserts.nonNull(builder.name, "name");
        this.description = Asserts.nonNull(builder.description, "description");
    }

    public static GenreDto of(String uuid) {
        return GenreDto.builder()
                .setUuid(fromString(uuid))
                .setName("")
                .setDescription("")
                .build();
    }

    @Nullable
    public UUID getUuid() {
        return uuid;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreDto genre = (GenreDto) o;
        return Objects.equals(uuid, genre.uuid)
                && Objects.equals(name, genre.name)
                && Objects.equals(description, genre.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, description);
    }

    @Override
    public String toString() {
        return "GenreDto{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private UUID uuid;
        private String name;
        private String description;

        private Builder() {
        }

        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public GenreDto build() {
            return new GenreDto(this);
        }
    }
}
