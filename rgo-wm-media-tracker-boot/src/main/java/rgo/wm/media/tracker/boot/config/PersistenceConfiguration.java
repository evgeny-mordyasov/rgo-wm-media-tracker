package rgo.wm.media.tracker.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.support.TransactionOperations;
import rgo.wm.media.tracker.persistence.JdbcGenreRepository;
import rgo.wm.media.tracker.persistence.JdbcMediaRepository;
import rgo.wm.media.tracker.persistence.TxGenreRepositoryDecorator;
import rgo.wm.media.tracker.persistence.TxMediaRepositoryDecorator;
import rgo.wm.media.tracker.persistence.api.GenreRepository;
import rgo.wm.media.tracker.persistence.api.MediaRepository;
import rgo.wm.spring.jdbc.TxWrapper;

@Configuration
public class PersistenceConfiguration {

    @Bean
    public TxWrapper txWrapper(TransactionOperations to) {
        return new TxWrapper(to);
    }

    @Bean
    public GenreRepository genreRepository(TxWrapper txWrapper, JdbcClient jdbcClient) {
        return new TxGenreRepositoryDecorator(
                txWrapper,
                new JdbcGenreRepository(jdbcClient)
        );
    }

    @Bean
    public MediaRepository mediaRepository(TxWrapper txWrapper, JdbcClient jdbcClient) {
        return new TxMediaRepositoryDecorator(
                txWrapper,
                new JdbcMediaRepository(jdbcClient)
        );
    }
}
