package com.apress.prospring6.ten;

import com.apress.prospring6.ten.config.DataJpaCfg;
import com.apress.prospring6.ten.service.AlbumService;
import jakarta.annotation.PostConstruct;
import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@Sql({"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql"})
@SpringJUnitConfig(classes = {AlbumServiceTest.TestContainersConfig.class})
public class AlbumServiceTest extends TestContainersBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumServiceTest.class);

    @Autowired
    AlbumService albumService;

    @Test
    public void testFindWithReleaseDateGreaterThan() {
        var albums = albumService
                .findWithReleaseDateGreaterThan(LocalDate.of(2010, 1, 1))
                .peek(s -> LOGGER.info(s.toString())).toList();
        assertEquals(2, albums.size());
    }

    @Test
    public void testFindByTitle() {
        var albums = albumService
                .findByTitle("The")
                .peek(s -> LOGGER.info(s.toString())).toList();
        assertEquals(1, albums.size());
    }

    @Configuration
    @Import(DataJpaCfg.class)
    public static class TestContainersConfig {
        @Autowired
        Properties jpaProperties;

        @PostConstruct
        public void initialize() {
            jpaProperties.put(Environment.FORMAT_SQL, true);
            jpaProperties.put(Environment.USE_SQL_COMMENTS, true);
            jpaProperties.put(Environment.SHOW_SQL, true);
            jpaProperties.put(Environment.STATEMENT_BATCH_SIZE, 30);
        }
    }
}
