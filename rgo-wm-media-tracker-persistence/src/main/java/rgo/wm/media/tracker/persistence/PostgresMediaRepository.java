package rgo.wm.media.tracker.persistence;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import rgo.wm.common.utils.exceptions.KeyRetrievalException;
import rgo.wm.media.tracker.persistence.api.Media;
import rgo.wm.media.tracker.persistence.api.MediaRepository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class PostgresMediaRepository implements MediaRepository {

    private final JdbcClient jdbc;

    public PostgresMediaRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    @Nonnull
    @Override
    public List<Media> findAll() {
        return jdbc.sql(MediaQueries.findAll())
                .query(MEDIA_MAPPER)
                .list();
    }

    private Media findByUuid(String uuid) {
        return jdbc.sql(MediaQueries.findByUuid())
                .param("uuid", uuid)
                .query(MEDIA_MAPPER)
                .single();
    }

    @Nonnull
    @Override
    public Media save(@Nonnull Media media) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", media.getName())
                .addValue("year", media.getYear());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.sql(MediaQueries.save()).paramSource(params).update(keyHolder, "uuid");
        String uuid = keyHolder.getKeyAs(String.class);
        if (uuid == null) {
            throw new KeyRetrievalException("Failed to retrieve uuid after saving media.");
        }

        return findByUuid(uuid);
    }

    private static final RowMapper<Media> MEDIA_MAPPER = (rs, rowNum) -> Media.builder()
            .setUuid(UUID.fromString(rs.getString("uuid")))
            .setName(rs.getString("name"))
            .setYear(rs.getInt("year"))
            .build();

    private static class MediaQueries {

        private static final String TABLE_NAME = "media";

        public static String findAll() {
            return select();
        }

        public static String findByUuid() {
            return select() + " WHERE uuid = :uuid";
        }

        public static String save() {
            return """
                    INSERT INTO %s(name, year)
                    VALUES(:name, :year)
                    """.formatted(TABLE_NAME);
        }

        private static String select() {
            return """
                    SELECT uuid,
                           name,
                           year,
                      FROM %s
                    """.formatted(TABLE_NAME);
        }
    }
}
