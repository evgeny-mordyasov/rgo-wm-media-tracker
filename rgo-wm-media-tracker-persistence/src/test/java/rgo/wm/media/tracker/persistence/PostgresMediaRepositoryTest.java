package rgo.wm.media.tracker.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.KeyHolder;
import rgo.wm.common.utils.exceptions.KeyRetrievalException;
import rgo.wm.media.tracker.persistence.api.Media;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rgo.wm.common.test.utils.random.IntRandom.randomPositiveInt;
import static rgo.wm.common.test.utils.random.StringRandom.randomString;

@SuppressWarnings("unchecked")
class PostgresMediaRepositoryTest {

    private PostgresMediaRepository repository;
    private JdbcClient jdbc;

    @BeforeEach
    void setUp() {
        jdbc = mock(JdbcClient.class);
        repository = new PostgresMediaRepository(jdbc);
    }

    @Test
    void findAll_isEmpty() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbc.sql(any())).thenReturn(statementSpec);
        JdbcClient.MappedQuerySpec<?> querySpec = mock(JdbcClient.MappedQuerySpec.class);
        when(statementSpec.query(any(RowMapper.class))).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(List.of());

        List<Media> list = repository.findAll();

        assertThat(list).isEmpty();
    }

    @Test
    void findAll() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbc.sql(any())).thenReturn(statementSpec);
        JdbcClient.MappedQuerySpec<Media> querySpec = mock(JdbcClient.MappedQuerySpec.class);
        when(statementSpec.query(any(RowMapper.class))).thenReturn(querySpec);
        Media media = randomMedia();
        when(querySpec.list()).thenReturn(List.of(media));

        List<Media> list = repository.findAll();

        assertThat(list).isNotEmpty().contains(media);
    }

    @Test
    void save_keyRetrievalException() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbc.sql(any())).thenReturn(statementSpec);
        when(statementSpec.paramSource(any())).thenReturn(statementSpec);
        when(statementSpec.update(any(KeyHolder.class), eq("uuid"))).thenReturn(1);

        Media created = randomMedia();
        assertThatThrownBy(() -> repository.save(created))
                .isInstanceOf(KeyRetrievalException.class)
                .hasMessage("Failed to retrieve uuid after saving media.");
    }

    @Test
    void save() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbc.sql(any())).thenReturn(statementSpec);
        when(statementSpec.paramSource(any())).thenReturn(statementSpec);
        String uuid = randomString();
        doAnswer(invocation -> {
            KeyHolder keyHolder = invocation.getArgument(0);
            keyHolder.getKeyList().add(Map.of("uuid", uuid));
            return null;
        }).when(statementSpec).update(any(KeyHolder.class), eq("uuid"));

        when(statementSpec.param("uuid", uuid)).thenReturn(statementSpec);
        JdbcClient.MappedQuerySpec<Media> querySpec = mock(JdbcClient.MappedQuerySpec.class);
        when(statementSpec.query(any(RowMapper.class))).thenReturn(querySpec);
        Media created = randomMedia();
        when(querySpec.single()).thenReturn(
                Media.builder()
                        .setUuid(UUID.fromString(uuid))
                        .setName(created.getName())
                        .setYear(created.getYear())
                        .build());

        Media saved = repository.save(created);

        assertThat(saved.getUuid()).isNotNull();
        assertThat(saved.getUuid().toString()).hasToString(uuid);
        assertThat(saved.getName()).hasToString(created.getName());
        assertThat(saved.getYear()).isEqualTo(created.getYear());
    }

    private Media randomMedia() {
        return Media.builder()
                .setName(randomString())
                .setYear(randomPositiveInt())
                .build();
    }
}