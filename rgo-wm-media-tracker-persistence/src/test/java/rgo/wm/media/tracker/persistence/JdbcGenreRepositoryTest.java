package rgo.wm.media.tracker.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import rgo.wm.media.tracker.persistence.api.Genre;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rgo.wm.media.tracker.test.model.generator.persistence.GenreData.randomGenre;
import static rgo.wm.media.tracker.test.model.generator.persistence.GenreData.randomPersistentGenre;

@SuppressWarnings("unchecked")
class JdbcGenreRepositoryTest {

    private JdbcGenreRepository repository;
    private JdbcClient jdbc;

    @BeforeEach
    void setUp() {
        jdbc = mock(JdbcClient.class);
        repository = new JdbcGenreRepository(jdbc);
    }

    @Test
    void findAll_isEmpty() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbc.sql(any())).thenReturn(statementSpec);
        JdbcClient.MappedQuerySpec<?> querySpec = mock(JdbcClient.MappedQuerySpec.class);
        when(statementSpec.query(any(RowMapper.class))).thenReturn(querySpec);
        when(querySpec.list()).thenReturn(List.of());

        List<Genre> list = repository.findAll();

        assertThat(list).isEmpty();
    }

    @Test
    void findAll() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbc.sql(any())).thenReturn(statementSpec);
        JdbcClient.MappedQuerySpec<Genre> querySpec = mock(JdbcClient.MappedQuerySpec.class);
        when(statementSpec.query(any(RowMapper.class))).thenReturn(querySpec);
        Genre genre = randomGenre();
        when(querySpec.list()).thenReturn(List.of(genre));

        List<Genre> list = repository.findAll();

        assertThat(list).isNotEmpty().contains(genre);
    }

    @Test
    void findByUuid_empty() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbc.sql(any())).thenReturn(statementSpec);
        when(statementSpec.param(eq("uuid"), any())).thenReturn(statementSpec);
        JdbcClient.MappedQuerySpec<?> querySpec = mock(JdbcClient.MappedQuerySpec.class);
        when(statementSpec.query(any(RowMapper.class))).thenReturn(querySpec);
        when(querySpec.optional()).thenReturn(Optional.empty());
        UUID uuid = UUID.randomUUID();

        Optional<Genre> optional = repository.findByUuid(uuid);

        assertThat(optional).isEmpty();
    }

    @Test
    @SuppressWarnings("rawtypes")
    void findByUuid() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbc.sql(any())).thenReturn(statementSpec);
        when(statementSpec.param(eq("uuid"), any())).thenReturn(statementSpec);
        JdbcClient.MappedQuerySpec<?> querySpec = mock(JdbcClient.MappedQuerySpec.class);
        when(statementSpec.query(any(RowMapper.class))).thenReturn(querySpec);
        Genre genre = randomPersistentGenre();
        when(querySpec.optional()).thenReturn((Optional) Optional.of(genre));

        Optional<Genre> optional = repository.findByUuid(genre.getUuid());

        assertThat(optional)
                .isPresent()
                .contains(genre);
    }
}