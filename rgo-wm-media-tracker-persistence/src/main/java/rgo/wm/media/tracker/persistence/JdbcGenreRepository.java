package rgo.wm.media.tracker.persistence;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import rgo.wm.media.tracker.persistence.api.Genre;
import rgo.wm.media.tracker.persistence.api.GenreRepository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcGenreRepository implements GenreRepository {

    private final JdbcClient jdbc;

    public JdbcGenreRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    @Nonnull
    @Override
    public List<Genre> findAll() {
        return jdbc.sql(GenreQueries.findAll())
                .query(GENRE_MAPPER)
                .list();
    }

    @Override
    public Optional<Genre> findByUuid(UUID uuid) {
        return jdbc.sql(GenreQueries.findByUuid())
                .param("uuid", uuid)
                .query(GENRE_MAPPER)
                .optional();
    }

    private static final RowMapper<Genre> GENRE_MAPPER = (rs, rowNum) -> Genre.builder()
            .setUuid(UUID.fromString(rs.getString("uuid")))
            .setName(rs.getString("name"))
            .setDescription(rs.getString("description"))
            .build();

    private static class GenreQueries {

        private static final String TABLE_NAME = "genre";

        static String findAll() {
            return select();
        }

        static String findByUuid() {
            return select() + " WHERE uuid = :uuid";
        }

        private static String select() {
            return """
                    SELECT uuid,
                           name,
                           description
                      FROM %s
                    """.formatted(TABLE_NAME);
        }
    }
}
