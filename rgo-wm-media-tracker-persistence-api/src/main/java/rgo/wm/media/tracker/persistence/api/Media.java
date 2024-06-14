package rgo.wm.media.tracker.persistence.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class Media {

    private final UUID uuid;
    private final String name;
    private final int year;

    private Media(Builder builder) {
        this.uuid = builder.uuid;
        this.name = builder.name;
        this.year = builder.year;
    }

    @Nullable
    public UUID getUuid() {
        return uuid;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media mediaDto = (Media) o;
        return year == mediaDto.year && Objects.equals(uuid, mediaDto.uuid) && Objects.equals(name, mediaDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, year);
    }

    @Override
    public String toString() {
        return "Media{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private UUID uuid;
        private String name;
        private int year;

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

        public Builder setYear(int year) {
            this.year = year;
            return this;
        }

        public Media build() {
            return new Media(this);
        }
    }
}
